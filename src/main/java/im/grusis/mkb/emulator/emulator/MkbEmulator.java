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

  public static final int MazeEnergyExpend = 2;

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
      TemporaryProfile token = new TemporaryProfile(ret.getGS_NAME(), ret.getGS_IP(), username, password, ret.getU_ID(), mac, ret.getKey(), ret.getTimestamp(), System.currentTimeMillis());
      profiles.put(username, token);
      MkbAccount account = accountService.findAccountByUsername(username);
      if(account == null) {
        account = new MkbAccount();
        account.setUsername(username);
        account.setPassword(password);
        account.setMac(mac);
        account.setServer(ret.getGS_NAME());
        accountService.saveAccount(account);
        archiveService.addUsername(username);
      }
    }
    return ret;
  }

  public boolean webReg(String username, String password, String mac, long serverId) throws UnknownErrorException {
    if(archiveService.existUsername(username)) {
      Log.debug("Cannot register account. Username {} is already in use", username);
      return false;
    }
    RegUserResponse resp = passportRequest(new RegUserRequest(username, password, mac, serverId), RegUserResponse.class);
    if(resp.badRequest()) {
      if(resp.duplicateUsername()) {
        Log.debug("Cannot register account. Username {} is already in use", username);
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

  public<T extends GameData> T gameDoAction(String username, String service, String action, Map<String, String> paramMap, Class<T> clazz) throws ServerNotAvailableException {
    String responseString = gameDoAction(username, service, action, paramMap);
    T response = GameDataFactory.getGameData(responseString, clazz);
    if(response.badRequest()) {
      Log.error("*** ERROR *** {}", responseString);
    }
    return response;
  }

  public PassportLogin gamePassportLogin(String username) throws ServerNotAvailableException {
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
    PassportLoginResponse response = gameDoAction(username, "login.php", "PassportLogin", paramMap, PassportLoginResponse.class);

    return response.getData();
  }

  public boolean gameSetNickname(String username, int sex, String inviteCode, String nickname) throws ServerNotAvailableException, UnknownErrorException {
    TemporaryProfile profile = profiles.get(username);
    if(archiveService.existNickname(profile.getServerName(), nickname)) {
      Log.debug("Cannot set nickname for {}. {} is already in use", username, nickname);
      return false;
    }
    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("Sex", Integer.toString(sex));
    paramMap.put("InviteCode", inviteCode);
    paramMap.put("NickName", nickname);
    EditNickNameResponse response = gameDoAction(username, "user.php", "EditNickName", paramMap, EditNickNameResponse.class);
    if(response.badRequest()) {
      if(response.duplicateNickName()) {
        Log.debug("Cannot set nickname for {}. {} is already in use", username, nickname);
        archiveService.addNickname(profile.getServerName(), nickname);
      } else if(response.tooLong()) {
        Log.error("Cannot set nickname for {}. {} has too many characters", username, nickname);
      } else {
        throw new UnknownErrorException();
      }
      return false;
    }
    archiveService.addNickname(profile.getServerName(), nickname);
    return true;
  }

  public int gamePurchase(String username, int goodsId) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("GoodsId", Integer.toString(goodsId));
    BuyResponse response = gameDoAction(username, "shop.php", "Buy", paramMap, BuyResponse.class);
    if(response.badRequest()) {
      if(response.noCurrency()) {
        Log.error("Cannot purchase goods {}. {} does not have enough currency", goodsId, username);
      } else {
        throw new UnknownErrorException();
      }
      return -1;
    }
    return response.getData();
  }

  public boolean gameSkipTutorial(String username, String stage) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("FreshStep", stage);
    EditFreshResponse response = gameDoAction(username, "user.php", "EditFresh", paramMap, EditFreshResponse.class);
    if(response.badRequest()) {
      if(response.alreadyFinished()) {
        Log.error("Cannot skip tutorial. {} has already finished tutorial stage {}", username, stage);
        return false;
      } else {
        throw new UnknownErrorException();
      }
    }
    return true;
  }

  public boolean gameGetRewards(String username) throws ServerNotAvailableException, UnknownErrorException {
    UserSalaryResponse response = gameDoAction(username, "user.php", "GetUserSalary", null, UserSalaryResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public boolean gameAcceptRewards(String username) throws ServerNotAvailableException, UnknownErrorException {
    UserSalaryResponse response = gameDoAction(username, "user.php", "AwardSalary", null, UserSalaryResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public AllCard gameGetCards(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException {
    AllCard cards;
    if(refresh || (cards = assetsService.getCards()) == null) {
      AllCardResponse response = gameDoAction(username, "card.php", "GetAllCard", null, AllCardResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      cards = response.getData();
      assetsService.saveAssets(cards);
    }

    return cards;
  }

  public Card gameGetCardDetail(String username, int cardId) throws ServerNotAvailableException, UnknownErrorException {
    Card card = assetsService.findCard(cardId);
    if(card == null) {
      gameGetCards(username, true);
      card = assetsService.findCard(cardId);
      if(card == null) {
        throw new UnknownErrorException();
      }
    }
    return card;
  }

  public Runes gameGetRunes(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException {
    Runes runes;
    if(refresh || (runes = assetsService.getRunes()) == null) {
      AllRuneResponse response = gameDoAction(username, "rune.php", "GetAllRune", null, AllRuneResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      runes = response.getData();
      assetsService.saveAssets(runes);
    }
    return runes;
  }

  public Rune gameGetRuneDetail(String username, int runeId) throws ServerNotAvailableException, UnknownErrorException {
    Rune rune = assetsService.findRune(runeId);
    if(rune == null) {
      gameGetRunes(username, true);
      rune = assetsService.findRune(runeId);
      if(rune == null) {
        throw new UnknownErrorException();
      }
    }
    return rune;
  }

  public AllSkill gameGetSkills(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException {
    AllSkill skills;
    if(refresh || (skills = assetsService.getSkills()) == null) {
      AllSkillResponse response = gameDoAction(username, "card.php", "GetAllSkill", null, AllSkillResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      skills = response.getData();
      assetsService.saveAssets(skills);
    }
    return skills;
  }

  public Skill gameGetSkillDetail(String username, int skillId) throws ServerNotAvailableException, UnknownErrorException {
    Skill skill = assetsService.findSkill(skillId);
    if(skill == null) {
      gameGetSkills(username, true);
      skill = assetsService.findSkill(skillId);
      if(skill == null) {
        throw new UnknownErrorException();
      }
    }
    return skill;
  }

  public MapStageAll gameGetMapStages(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException {
    MapStageAll stages;
    if(refresh || (stages = assetsService.getStages()) == null) {
      MapStageAllResponse response = gameDoAction(username, "mapstage.php", "GetMapStageALL", null, MapStageAllResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      stages = response.getData();
      assetsService.saveAssets(stages);
    }
    return stages;
  }

  public MapStage gameGetMapStage(String username, int stageId) throws ServerNotAvailableException, UnknownErrorException {
    MapStage mapStage = assetsService.findMapStage(stageId);
    if(mapStage == null) {
      gameGetMapStages(username, true);
      mapStage = assetsService.findMapStage(stageId);
      if(mapStage == null) {
        throw new UnknownErrorException();
      }
    }
    return mapStage;
  }

  public MapStageDetail gameGetMapStageDetail(String username, int stageDetailId) throws ServerNotAvailableException, UnknownErrorException {
    MapStageDetail mapStageDetail = assetsService.findMapStageDetail(stageDetailId);
    if(mapStageDetail == null) {
      MapStageAllResponse response = gameDoAction(username, "mapstage.php", "GetMapStageALL", null, MapStageAllResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      MapStageAll mapStages = response.getData();
      assetsService.saveAssets(mapStages);
      mapStageDetail = assetsService.findMapStageDetail(stageDetailId);
      if(mapStageDetail == null) {
        throw new UnknownErrorException();
      }
    }
    return mapStageDetail;
  }

  public UserInfo gameGetUserInfo(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException {
    MkbAccount account = accountService.findAccountByUsername(username);
    UserInfo userInfo;
    if(refresh || (userInfo = account.getUserInfo()) == null) {
      UserInfoResponse response = gameDoAction(username, "user.php", "GetUserinfo", null, UserInfoResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      userInfo = response.getData();
      account.setUserInfo(userInfo);
      account.setInviteCode(userInfo.getInviteCode());
      account.setInviteCount(userInfo.getInviteNum());
      accountService.saveAccount(account);
      archiveService.addNickname(profiles.get(username).getServerName(), userInfo.getNickName());
    }
    return userInfo;
  }

  public UserCards gameGetUserCards(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException {
    MkbAccount account = accountService.findAccountByUsername(username);
    UserCards cards;
    if(refresh || (cards = account.getUserCards()) == null) {
      UserCardsResponse response = gameDoAction(username, "card.php", "GetUserCards", null, UserCardsResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      cards = response.getData();
      account.setUserCards(cards);
      accountService.saveAccount(account);
    }
    return cards;
  }

  public CardGroup gameGetCardGroup(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException {
    MkbAccount account = accountService.findAccountByUsername(username);
    CardGroup cardGroup;
    if(refresh || (cardGroup = account.getCardGroup()) == null) {
      CardGroupResponse response = gameDoAction(username, "card.php", "GetCardGroup", null, CardGroupResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      cardGroup = response.getData();
      account.setCardGroup(cardGroup);
      accountService.saveAccount(account);
    }
    return cardGroup;
  }

  public UserMapStages gameGetUserMapStages(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException {
    MkbAccount account = accountService.findAccountByUsername(username);
    UserMapStages stages;
    if(refresh || (stages = account.getUserMapStages()) == null) {
      UserMapStagesResponse response = gameDoAction(username, "mapstage.php", "GetUserMapStages", null, UserMapStagesResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      stages = response.getData();
      account.setUserMapStages(stages);
      accountService.saveAccount(account);
    }

    return stages;
  }

  public UserMapStage gameGetUserMapStage(String username, int userMapStageDetailId) throws ServerNotAvailableException, UnknownErrorException {
    UserMapStages stages = gameGetUserMapStages(username, false);
    return stages.get(userMapStageDetailId);
  }

  public UserChip gameGetUserChip(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException {
    MkbAccount account = accountService.findAccountByUsername(username);
    UserChip userChip;
    if(refresh || (userChip = account.getUserChip()) == null) {
      UserChipResponse response = gameDoAction(username, "chip.php", "GetUserChip", null, UserChipResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      userChip = response.getData();
      account.setUserChip(userChip);
      accountService.saveAccount(account);
    }
    return userChip;
  }

  private void processBattleMapResult(MkbAccount account, BattleMap result) {
    BattleMapExtData ext = result.getExtData();
    if(ext != null) {
    }
  }

  private void processBattleMazeResult(MkbAccount account, BattleMaze result, int mapStageId) {
    BattleMazeExtData ext = result.getExtData();
    if(ext != null) {
      BattleMazeExtData.User user = ext.getUser();
      account.setLevel(user.getLevel());
      account.addExp(user.getExp());

      BattleMazeExtData.Clear clear = ext.getClear();
      account.addGold(clear.getCoins());
      if(clear.getCardId() > 0) account.addNewCard(clear.getCardId());
      if(clear.getIsClear() > 0) account.clearMaze(mapStageId);
      account.addGold(clear.getCoins());

      BattleMazeExtData.Award award = ext.getAward();
      if(award.getCardId() > 0) account.addNewCard(award.getCardId());
      account.addGold(award.getCoins());
      account.addExp(award.getExp());
    }
  }

  public Level gameFindLevel(String username, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException {
    MapStageDetail stage = gameGetMapStageDetail(username, mapStageDetailId);
    UserMapStage userStage = gameGetUserMapStage(username, mapStageDetailId);
    List<Level> levels = stage.getLevels();
    int finished = userStage.getFinishedStage();
    if(finished >= levels.size()) {
      finished = levels.size() - 1;
    }
    return levels.get(finished);
  }

  public BattleMap gameMapBattleAuto(String username, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageDetailId", Integer.toString(mapStageDetailId));
    params.put("isManual", Integer.toString(0));
    BattleMapResponse response = gameDoAction(username, "mapstage.php", "EditUserMapStages", params, BattleMapResponse.class);
    if(response.badRequest()) {
      if(response.noEnergy()) {
        return null;
      }
      throw new UnknownErrorException();
    }
    MkbAccount account = accountService.findAccountByUsername(username);
    account.useEnergy(gameFindLevel(username, mapStageDetailId).getEnergyExpend());
    BattleMap result = response.getData();
    processBattleMapResult(account, result);
    return result;
  }

  public BattleMaze gameMazeBattleAuto(String username, int mapStageId, int layer, int itemIndex) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("manual", Integer.toString(0));
    params.put("MapStageId", Integer.toString(mapStageId));
    params.put("Layer", Integer.toString(layer));
    params.put("ItemIndex", Integer.toString(itemIndex));
    BattleMazeResponse response = gameDoAction(username, "maze.php", "Battle", params, BattleMazeResponse.class);
    if(response.badRequest()) {
      if(response.noEnergy()) {
        return null;
      }
      throw new UnknownErrorException();
    }
    MkbAccount account = accountService.findAccountByUsername(username);
    account.useEnergy(MazeEnergyExpend);
    BattleMaze result = response.getData();
    processBattleMazeResult(account, result, mapStageId);
    return result;
  }

  public MazeInfo gameGetMazeLayer(String username, int mapStageId, int layer) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mapStageId));
    params.put("Layer", Integer.toString(layer));
    MazeInfoResponse response = gameDoAction(username, "maze.php", "Info", params, MazeInfoResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public MazeShow gameGetMaze(String username, int mapStageId) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mapStageId));
    MazeShowResponse response = gameDoAction(username, "maze.php", "Show", params, MazeShowResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public boolean gameResetMaze(String username, int mapStageId) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mapStageId));
    MazeResetResponse response = gameDoAction(username, "maze.php", "Reset", params, MazeResetResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public boolean gameAcceptStageClearReward(String username, int mapStageId) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mapStageId));
    AwardClearResponse response = gameDoAction(username, "mapstage.php", "AwardClear", params, AwardClearResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public Explore gameExplore(String username, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageDetailId", Integer.toString(mapStageDetailId));
    ExploreResponse response = gameDoAction(username, "mapstage.php", "Explore", params, ExploreResponse.class);
    MkbAccount account = accountService.findAccountByUsername(username);
    account.useEnergy(gameFindLevel(username, mapStageDetailId).getEnergyExplore());
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public Thieves gameGetThieves(String username) throws ServerNotAvailableException, UnknownErrorException {
    ThievesResponse response = gameDoAction(username, "arena.php", "GetThieves", null, ThievesResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public GoodsList gameGetGoods(String username) throws ServerNotAvailableException, UnknownErrorException {
    GoodsResponse response = gameDoAction(username, "shop.php", "GetGoods", null, GoodsResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

}
