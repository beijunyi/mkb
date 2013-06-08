package im.grusis.mkb.emulator.emulator;

import java.util.*;

import im.grusis.mkb.emulator.emulator.core.MkbCore;
import im.grusis.mkb.emulator.emulator.core.model.basic.*;
import im.grusis.mkb.emulator.emulator.core.model.response.*;
import im.grusis.mkb.emulator.emulator.passport.PassportHelper;
import im.grusis.mkb.emulator.emulator.passport.model.basic.LoginInformation;
import im.grusis.mkb.emulator.emulator.passport.model.basic.ServerInformation;
import im.grusis.mkb.emulator.emulator.passport.model.request.*;
import im.grusis.mkb.emulator.emulator.passport.model.response.*;
import im.grusis.mkb.exception.ServerNotAvailableException;
import im.grusis.mkb.exception.UnknownErrorException;
import im.grusis.mkb.internal.MkbAccount;
import im.grusis.mkb.service.AccountService;
import im.grusis.mkb.service.ArchiveService;
import im.grusis.mkb.service.AssetsService;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * User: beij
 * Date: 23/01/13
 * Time: 15:13
 */

@Component
@PropertySource("classpath:/mkb.properties")
public class MkbEmulator {

  private static final Logger Log = LoggerFactory.getLogger(MkbEmulator.class);

  @Value("${platform}") private String platform;
  @Value("${language}") private String language;
  @Value("${versionClient}") private String versionClient;
  @Value("${versionBuild}") private String versionBuild;

  @Autowired AccountService accountService;
  @Autowired ArchiveService archiveService;
  @Autowired AssetsService assetsService;

  private ClientConnectionManager connectionManager = new PoolingClientConnectionManager();
  private DefaultHttpClient shared = new DefaultHttpClient(connectionManager);
  private Map<String, DefaultHttpClient> httpClients = new HashMap<String, DefaultHttpClient>();
  private List<DefaultHttpClient> freeClients = new ArrayList<DefaultHttpClient>();

  private Map<String, TemporaryProfile> profiles = new HashMap<String, TemporaryProfile>();
  private Map<String, MkbCore> cores = new HashMap<String, MkbCore>();

  private DefaultHttpClient getHttpClient(String username) {
    DefaultHttpClient httpClient = httpClients.get(username);
    if(httpClient == null) {
      if(!freeClients.isEmpty()) {
        httpClient = freeClients.remove(0);
      } else {
        httpClient = new DefaultHttpClient(connectionManager);
      }
      httpClients.put(username, httpClient);
    }
    return httpClient;
  }

  public void garbageAccount(String username) {

  }

  public MkbCore getMkbCore(String username) {
    MkbCore core = cores.get(username);
    if(core == null) {
      TemporaryProfile profile = profiles.get(username);
      if(profile == null) {
        Log.error("Cannot create core. {} does not have a temporary profile", username);
        return null;
      }
      core = new MkbCore(profile.getHost(), getHttpClient(username), platform, language, versionClient, versionBuild);
      cores.put(username, core);
    }
    return core;
  }

  public <T extends ReturnTemplate> T passportRequest(PassportRequest<T> passportRequest, Class<T> clazz) {
    PassportHelper helper = new PassportHelper(shared);
    helper.requestEncryptKey();
    helper.proposeCounterKey();
    return helper.sendRequest(passportRequest, clazz);
  }

  public LoginInformation webLogin(String username) {
    MkbAccount account = accountService.findAccountByUsername(username);
    if(account == null) {
      Log.error("Cannot login account {}. There is no record in the database of this account.", username);
      return null;
    }
    return webLogin(username, account.getPassword(), account.getMac());
  }

  public LoginInformation webLogin(String username, String password, String mac) {
    if(username == null) {
      Log.error("Cannot login account without a username");
      return null;
    }
    if(password == null) {
      Log.error("Cannot login account {} without a password", password);
      return null;
    }
    if(mac == null) {
      Log.error("Cannot login account {} without a MAC address", mac);
      return null;
    }
    LoginInformationResponse loginInformation = passportRequest(new LoginRequest(username, password, mac), LoginInformationResponse.class);
    LoginInformation ret = loginInformation.getReturnObjs();
    if(!loginInformation.badRequest()) {
      archiveService.addUsername(username);
      TemporaryProfile token = new TemporaryProfile(ret.getGS_IP(), username, password, ret.getU_ID(), mac, ret.getKey(), ret.getTimestamp(), System.currentTimeMillis());
      profiles.put(username, token);
      MkbAccount account = accountService.findAccountByUsername(username);
      if(account == null) {
        account = new MkbAccount();
        account.setUsername(username);
        account.setPassword(password);
        account.setMac(mac);
        accountService.saveAccount(account);
        archiveService.addUsername(username);
      }
    }
    return ret;
  }

  public boolean webReg(String username, String password, String mac, long serverId) throws UnknownErrorException {
    RegUserResponse resp = passportRequest(new RegUserRequest(username, password, mac, serverId), RegUserResponse.class);
    if(resp.badRequest()) {
      if(resp.duplicateUsername()) {
        Log.error("Cannot register account. Username {} is already in use", username);
        archiveService.addUsername(username);
        return false;
      }
      throw new UnknownErrorException();
    }
    MkbAccount account = new MkbAccount();
    account.setUsername(username);
    account.setPassword(password);
    account.setMac(mac);
    accountService.saveAccount(account);
    archiveService.addUsername(username);
    return true;
  }

  public ServerInformation webGetGameServer() {
    return passportRequest(new ServerRequest(), ServerInformationResponse.class).getReturnObjs();
  }

  public String gameDoAction(String username, String service, String action, Map<String, String> paramMap) throws ServerNotAvailableException {
    TemporaryProfile profile = profiles.get(username);
    if(profile == null) {
      Log.error("Cannot perform {}/{}. {} does not have a temporary profile", service, action, username);
      return null;
    }
    profile.setLastAccess(System.currentTimeMillis());
    MkbCore core = getMkbCore(username);
    return core.doAction(service, action, paramMap);
  }

  public PassportLogin gamePassportLogin(String username) throws ServerNotAvailableException, UnknownErrorException {
    TemporaryProfile profile = profiles.get(username);
    if(profile == null) {
      Log.error("Cannot do passport login. {} does not have a temporary profile", username);
      return null;
    }
    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("Devicetoken", "");
    paramMap.put("time", Long.toString(profile.getTime()));
    paramMap.put("key", profile.getKey());
    paramMap.put("Origin", "TTGM");
    paramMap.put("Udid", profile.getMac());
    paramMap.put("UserName", username);
    paramMap.put("Password", Long.toString(profile.getUid()));
    String responseString = gameDoAction(username, "login.php", "PassportLogin", paramMap);
    PassportLoginResponse response = GameDataFactory.getGameData(responseString, PassportLoginResponse.class);
    if(response.badRequest()) {
      Log.error("*** UNKNOWN ERROR *** {}", responseString);
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public boolean gameSetNickname(String username, int sex, String inviteCode, String nickname) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("Sex", Integer.toString(sex));
    paramMap.put("InviteCode", inviteCode);
    paramMap.put("NickName", nickname);
    String responseString = gameDoAction(username, "user.php", "EditNickName", paramMap);
    EditNickNameResponse response = GameDataFactory.getGameData(responseString, EditNickNameResponse.class);
    if(response.badRequest()) {
      if(response.duplicateNickName()) {
        Log.error("Cannot set nickname for {}. {} is already in use", username, nickname);
      } else if(response.tooLong()) {
        Log.error("Cannot set nickname for {}. {} has too many characters", username, nickname);
      } else {
        Log.error("*** UNKNOWN ERROR *** {}", responseString);
        throw new UnknownErrorException();
      }
      return false;
    }
    return true;
  }

  public int gamePurchase(String username, int goodsId) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("GoodsId", Integer.toString(goodsId));
    String responseString = gameDoAction(username, "shop.php", "Buy", paramMap);
    BuyResponse response = GameDataFactory.getGameData(responseString, BuyResponse.class);
    if(response.badRequest()) {
      if(response.noCurrency()) {
        Log.error("Cannot purchase goods {}. {} does not have enough currency", goodsId, username);
      } else {
        Log.error("*** UNKNOWN ERROR *** {}", responseString);
        throw new UnknownErrorException();
      }
      return -1;
    }
    return response.getData();
  }

  public boolean gameSkipTutorial(String username, String stage) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("FreshStep", stage);
    String responseString = gameDoAction(username, "user.php", "EditFresh", paramMap);
    EditFreshResponse response = GameDataFactory.getGameData(responseString, EditFreshResponse.class);
    if(response.badRequest()) {
      if(response.alreadyFinished()) {
        Log.error("Cannot skip tutorial. {} has already finished tutorial stage {}", username, stage);
        return false;
      } else {
        Log.error("*** UNKNOWN ERROR *** {}", responseString);
        throw new UnknownErrorException();
      }
    }
    return true;
  }

  public boolean gameGetRewards(String username) throws ServerNotAvailableException, UnknownErrorException {
    String responseString = gameDoAction(username, "user.php", "GetUserSalary", null);
    UserSalaryResponse response = GameDataFactory.getGameData(responseString, UserSalaryResponse.class);
    if(response.badRequest()) {
      Log.error("*** UNKNOWN ERROR *** {}", responseString);
      throw new UnknownErrorException();
    }
    return true;
  }

  public boolean gameAcceptRewards(String username) throws ServerNotAvailableException, UnknownErrorException {
    String responseString = gameDoAction(username, "user.php", "AwardSalary", null);
    UserSalaryResponse response = GameDataFactory.getGameData(responseString, UserSalaryResponse.class);
    if(response.badRequest()) {
      Log.error("*** UNKNOWN ERROR *** {}", responseString);
      throw new UnknownErrorException();
    }
    return true;
  }

  public Card gameGetCardDetail(String username, int cardId) throws ServerNotAvailableException, UnknownErrorException {
    Card card = assetsService.findCard(cardId);
    if(card == null) {
      String responseString = gameDoAction(username, "card.php", "GetAllCard", null);
      AllCardResponse response = GameDataFactory.getGameData(responseString, AllCardResponse.class);
      if(response.badRequest()) {
        Log.error("*** UNKNOWN ERROR *** {}", responseString);
        throw new UnknownErrorException();
      }
      AllCard cards = response.getData();
      assetsService.saveAssets(cards);
      card = assetsService.findCard(cardId);
      if(card == null) {
        Log.error("*** UNKNOWN ERROR *** {}", responseString);
        throw new UnknownErrorException();
      }
    }
    return card;
  }

  public Rune gameGetRuneDetail(String username, int runeId) throws ServerNotAvailableException, UnknownErrorException {
    Rune rune = assetsService.findRune(runeId);
    if(rune == null) {
      String responseString = gameDoAction(username, "rune.php", "GetAllRune", null);
      AllRuneResponse response = GameDataFactory.getGameData(responseString, AllRuneResponse.class);
      if(response.badRequest()) {
        Log.error("*** UNKNOWN ERROR *** {}", responseString);
        throw new UnknownErrorException();
      }
      Runes runes = response.getData();
      assetsService.saveAssets(runes);
      rune = assetsService.findRune(runeId);
      if(rune == null) {
        Log.error("*** UNKNOWN ERROR *** {}", responseString);
        throw new UnknownErrorException();
      }
    }
    return rune;
  }

  public Skill gameGetSkillDetail(String username, int skillId) throws ServerNotAvailableException, UnknownErrorException {
    Skill skill = assetsService.findSkill(skillId);
    if(skill == null) {
      String responseString = gameDoAction(username, "card.php", "GetAllSkill", null);
      AllSkillResponse response = GameDataFactory.getGameData(responseString, AllSkillResponse.class);
      if(response.badRequest()) {
        Log.error("*** UNKNOWN ERROR *** {}", responseString);
        throw new UnknownErrorException();
      }
      AllSkill skills = response.getData();
      assetsService.saveAssets(skills);
      skill = assetsService.findSkill(skillId);
      if(skill == null) {
        Log.error("*** UNKNOWN ERROR *** {}", responseString);
        throw new UnknownErrorException();
      }
    }
    return skill;
  }

  public MapStage gameGetMapStage(String username, int stageId) throws ServerNotAvailableException, UnknownErrorException {
    MapStage mapStage = assetsService.findMapStage(stageId);
    if(mapStage == null) {
      String responseString = gameDoAction(username, "mapstage.php", "GetMapStageALL", null);
      MapStageAllResponse response = GameDataFactory.getGameData(responseString, MapStageAllResponse.class);
      if(response.badRequest()) {
        Log.error("*** UNKNOWN ERROR *** {}", responseString);
        throw new UnknownErrorException();
      }
      MapStageAll mapStages = response.getData();
      assetsService.saveAssets(mapStages);
      mapStage = assetsService.findMapStage(stageId);
      if(mapStage == null) {
        Log.error("*** UNKNOWN ERROR *** {}", responseString);
        throw new UnknownErrorException();
      }
    }
    return mapStage;
  }

  public MapStageDetail gameGetMapStageDetail(String username, int stageDetailId) throws ServerNotAvailableException, UnknownErrorException {
    MapStageDetail mapStageDetail = assetsService.findMapStageDetail(stageDetailId);
    if(mapStageDetail == null) {
      String responseString = gameDoAction(username, "mapstage.php", "GetMapStageALL", null);
      MapStageAllResponse response = GameDataFactory.getGameData(responseString, MapStageAllResponse.class);
      if(response.badRequest()) {
        Log.error("*** UNKNOWN ERROR *** {}", responseString);
        throw new UnknownErrorException();
      }
      MapStageAll mapStages = response.getData();
      assetsService.saveAssets(mapStages);
      mapStageDetail = assetsService.findMapStageDetail(stageDetailId);
      if(mapStageDetail == null) {
        Log.error("*** UNKNOWN ERROR *** {}", responseString);
        throw new UnknownErrorException();
      }
    }
    return mapStageDetail;
  }

  public UserInfo gameGetUserInfo(String username) throws ServerNotAvailableException, UnknownErrorException {
    String responseString = gameDoAction(username, "user.php", "GetUserinfo", null);
    UserInfoResponse response = GameDataFactory.getGameData(responseString, UserInfoResponse.class);
    if(response.badRequest()) {
      Log.error("*** UNKNOWN ERROR *** {}", responseString);
      throw new UnknownErrorException();
    }
    UserInfo result = response.getData();
    MkbAccount account = accountService.findAccountByUsername(username);
    account.setUserInfo(result);
    account.setInviteCode(result.getInviteCode());
    account.setInviteCount(result.getInviteNum());
    accountService.saveAccount(account);
    return result;
  }

  public UserCards gameGetUserCards(String username) throws ServerNotAvailableException, UnknownErrorException {
    String responseString = gameDoAction(username, "card.php", "GetUserCards", null);
    UserCardsResponse response = GameDataFactory.getGameData(responseString, UserCardsResponse.class);
    if(response.badRequest()) {
      Log.error("*** UNKNOWN ERROR *** {}", responseString);
      throw new UnknownErrorException();
    }
    UserCards result = response.getData();
    MkbAccount account = accountService.findAccountByUsername(username);
    account.setUserCards(result);
    accountService.saveAccount(account);
    return result;
  }

  public CardGroup gameGetCardGroup(String username) throws ServerNotAvailableException, UnknownErrorException {
    String responseString = gameDoAction(username, "card.php", "GetCardGroup", null);
    CardGroupResponse response = GameDataFactory.getGameData(responseString, CardGroupResponse.class);
    if(response.badRequest()) {
      Log.error("*** UNKNOWN ERROR *** {}", responseString);
      throw new UnknownErrorException();
    }
    CardGroup result = response.getData();
    MkbAccount account = accountService.findAccountByUsername(username);
    account.setCardGroup(result);
    accountService.saveAccount(account);
    return result;
  }

  public UserMapStages gameGetUserMapStage(String username) throws ServerNotAvailableException, UnknownErrorException {
    String responseString = gameDoAction(username, "mapstage.php", "GetUserMapStages", null);
    UserMapStagesResponse response = GameDataFactory.getGameData(responseString, UserMapStagesResponse.class);
    if(response.badRequest()) {
      Log.error("*** UNKNOWN ERROR *** {}", responseString);
      throw new UnknownErrorException();
    }
    UserMapStages result = response.getData();
    MkbAccount account = accountService.findAccountByUsername(username);
    account.setUserMapStages(result);
    accountService.saveAccount(account);
    return result;
  }

  private void processBattleMapResult(String username, BattleMap result) {
    BattleMapExtData ext = result.getExtData();
    if(ext != null) {
      MkbAccount account = accountService.findAccountByUsername(username);
      accountService.saveAccount(account);
    }
  }

  private void processBattleMazeResult(String username, BattleMaze result) {
    BattleMazeExtData ext = result.getExtData();
    if(ext != null) {
      MkbAccount account = accountService.findAccountByUsername(username);
      BattleMazeExtData.User user = ext.getUser();
      if(user != null) {
        UserInfo userInfo = account.getUserInfo();
        if(userInfo == null) {
          userInfo = new UserInfo();
          account.setUserInfo(userInfo);
        }
        int level = user.getLevel();
        long exp = user.getExp();
        long prevExp = user.getNextExp();
        long nextExp = user.getNextExp();
        if(level > 0) userInfo.setLevel(level);
        if(exp > 0) userInfo.setExp(exp);
        if(prevExp > 0) userInfo.setPrevExp(prevExp);
        if(nextExp > 0) userInfo.setNextExp(nextExp);
      }
      accountService.saveAccount(account);
    }
  }

  public BattleMap gameMapBattleAuto(String username, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageDetailId", Integer.toString(mapStageDetailId));
    params.put("isManual", Integer.toString(0));
    String responseString = gameDoAction(username, "mapstage.php", "EditUserMapStages", params);
    BattleMapResponse response = GameDataFactory.getGameData(responseString, BattleMapResponse.class);
    if(response.badRequest()) {
      Log.error("*** UNKNOWN ERROR *** {}", responseString);
      throw new UnknownErrorException();
    }
    BattleMap result = response.getData();
    processBattleMapResult(username, result);
    return result;
  }

  public BattleMaze gameMazeBattleAuto(String username, int mapStageId, int layer, int itemIndex) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("manual", Integer.toString(0));
    params.put("MapStageId", Integer.toString(mapStageId));
    params.put("Layer", Integer.toString(layer));
    params.put("ItemIndex", Integer.toString(itemIndex));
    String responseString = gameDoAction(username, "maze.php", "Battle", params);
    BattleMazeResponse response = GameDataFactory.getGameData(responseString, BattleMazeResponse.class);
    if(response.badRequest()) {
      Log.error("*** UNKNOWN ERROR *** {}", responseString);
      throw new UnknownErrorException();
    }
    BattleMaze result = response.getData();
    processBattleMazeResult(username, result);
    return result;
  }

  public MazeInfo gameGetMazeLayer(String username, int mapStageId, int layer) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mapStageId));
    params.put("Layer", Integer.toString(layer));
    String responseString = gameDoAction(username, "maze.php", "Info", params);
    MazeInfoResponse response = GameDataFactory.getGameData(responseString, MazeInfoResponse.class);
    if(response.badRequest()) {
      Log.error("*** UNKNOWN ERROR *** {}", responseString);
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public MazeShow gameGetMaze(String username, int mapStageId) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mapStageId));
    String responseString = gameDoAction(username, "maze.php", "Show", params);
    MazeShowResponse response = GameDataFactory.getGameData(responseString, MazeShowResponse.class);
    if(response.badRequest()) {
      Log.error("*** UNKNOWN ERROR *** {}", responseString);
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public boolean gameAcceptStageClearReward(String username, int mapStageId) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mapStageId));
    String responseString = gameDoAction(username, "mapstage.php", "AwardClear", params);
    AwardClearResponse response = GameDataFactory.getGameData(responseString, AwardClearResponse.class);
    if(response.badRequest()) {
      Log.error("*** UNKNOWN ERROR *** {}", responseString);
      throw new UnknownErrorException();
    }
    return true;
  }

}
