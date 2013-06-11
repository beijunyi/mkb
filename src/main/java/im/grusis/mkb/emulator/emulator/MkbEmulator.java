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
import im.grusis.mkb.exception.WrongCredentialException;
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

  public MkbCore getMkbCore(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbCore core = cores.get(username);
    if(core == null) {
      TemporaryProfile profile = profiles.get(username);
      if(profile == null) {
        Log.info("Cannot create core. {} does not have a temporary profile. Now try to find history profile from database", username);
        MkbAccount account = accountService.findAccountByUsername(username);
        if(account == null) {
          Log.error("Cannot find any history record for account {}. Please login with username and password at least once", username);
          throw new WrongCredentialException();
        }
        profile = account.getProfile();
        if(profile == null) {
          Log.error("Cannot find history profile for account {}. Now try to do web login", username);
          profile = webLogin(username);
        }
        gamePassportLogin(username);
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

  public TemporaryProfile webLogin(String username) throws WrongCredentialException{
    MkbAccount account = accountService.findAccountByUsername(username);
    if(account == null) {
      Log.error("Cannot login account {}. There is no credential record in the database for this account.", username);
      throw new WrongCredentialException();
    }
    return webLogin(username, account.getPassword(), account.getMac());
  }

  public TemporaryProfile webLogin(String username, String password, String mac) throws WrongCredentialException {
    Log.debug("Starting web login for account {}", username);
    if(username == null) {
      Log.error("Cannot login account without a username");
      return null;
    }
    if(password == null) {
      Log.error("Cannot login account {} without a password", username);
      return null;
    }
    if(mac == null) {
      Log.error("Cannot login account {} without a MAC address", username);
      return null;
    }
    LoginInformationResponse loginInformation = passportRequest(new LoginRequest(username, password, mac), LoginInformationResponse.class);
    LoginInformation ret = loginInformation.getReturnObjs();
    if(!loginInformation.badRequest()) {
      archiveService.addUsername(username);
      TemporaryProfile profile = new TemporaryProfile(ret.getGS_NAME(), ret.getGS_IP(), username, password, ret.getU_ID(), mac, ret.getKey(), ret.getTimestamp());
      profiles.put(username, profile);
      MkbAccount account = accountService.findAccountByUsername(username);
      if(account == null) {
        account = new MkbAccount();
        account.setUsername(username);
        account.setPassword(password);
        account.setMac(mac);
        account.setServer(ret.getGS_NAME());
        archiveService.addUsername(username);
      }
      account.setProfile(profile);
      accountService.saveAccount(account);
      return profile;
    }
    Log.error("Cannot login account {}. Please check credential.", username);
    throw new WrongCredentialException();
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

  public String gameDoAction(String username, String service, String action, Map<String, String> paramMap) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbCore core = getMkbCore(username);
    String result = core.doAction(service, action, paramMap);
    accountService.findAccountByUsername(username).updateLastAction();
    return result;
  }

  public<T extends GameData> T gameDoAction(String username, String service, String action, Map<String, String> paramMap, Class<T> clazz) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    String responseString = gameDoAction(username, service, action, paramMap);
    T response = GameDataFactory.getGameData(responseString, clazz);
    if(response.badRequest()) {
      if(response.disconnected()) {
        Log.debug("Previous session is no longer valid. Now try passport login", username);
        gamePassportLogin(username);
        return gameDoAction(username, service, action, paramMap, clazz);
      }
      Log.error("*** ERROR *** {}", responseString);
    }
    return response;
  }

  public PassportLogin gamePassportLogin(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Log.debug("Starting passport login for account {}", username);
    TemporaryProfile profile = profiles.get(username);
    if(profile == null) {
      Log.debug("Cannot do passport login. {} does not have a temporary profile", username);
      MkbAccount account = accountService.findAccountByUsername(username);
      profile = account.getProfile();
      if(profile != null) {
        Log.debug("Login profile for account {} is found. Now continue passport login", username);
        profiles.put(username, profile);
      } else {
        Log.debug("Login profile for account {} is not found. Now try web login", username);
        profile = webLogin(username);
      }
    }
    Log.debug("Obtained temporary profile for account {} as login token", username);
    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("Devicetoken", "");
    paramMap.put("time", Long.toString(profile.getTime()));
    paramMap.put("key", profile.getKey());
    paramMap.put("Origin", "TTGM");
    paramMap.put("Udid", profile.getMac());
    paramMap.put("UserName", username);
    paramMap.put("Password", Long.toString(profile.getUid()));
    PassportLoginResponse response = gameDoAction(username, "login.php", "PassportLogin", paramMap, PassportLoginResponse.class);
    if(response.badRequest()) {
      Log.info("Passport login for account {} has failed. Now try web login", username);
      webLogin(username);
      return gamePassportLogin(username);
    }
    gameGetUserInfo(username, true);
    return response.getData();
  }

  public boolean gameSetNickname(String username, int sex, String inviteCode, String nickname) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    TemporaryProfile profile = profiles.get(username);
    if(archiveService.existNickname(profile.getServerName(), nickname)) {
      Log.debug("Cannot set nickname for account {}. {} is already in use", username, nickname);
      return false;
    }
    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("Sex", Integer.toString(sex));
    paramMap.put("InviteCode", inviteCode);
    paramMap.put("NickName", nickname);
    EditNickNameResponse response = gameDoAction(username, "user.php", "EditNickName", paramMap, EditNickNameResponse.class);
    if(response.badRequest()) {
      if(response.duplicateNickName()) {
        Log.debug("Cannot set nickname for account {}. Desired nickname {} is already in use", username, nickname);
        archiveService.addNickname(profile.getServerName(), nickname);
      } else if(response.tooLong()) {
        Log.error("Cannot set nickname for account {}. Desired nickname {} has too many characters", username, nickname);
      } else {
        throw new UnknownErrorException();
      }
      return false;
    }
    archiveService.addNickname(profile.getServerName(), nickname);
    return true;
  }

  public int gamePurchase(String username, int goodsId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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

  public boolean gameSkipTutorial(String username, String stage) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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

  public boolean gameGetRewards(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserSalaryResponse response = gameDoAction(username, "user.php", "GetUserSalary", null, UserSalaryResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public boolean gameAcceptRewards(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserSalaryResponse response = gameDoAction(username, "user.php", "AwardSalary", null, UserSalaryResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public AllCard gameGetCards(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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

  public Card gameGetCardDetail(String username, int cardId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    if(cardId <= 0) {
      Log.error("{} is an invalid card id. Card id must be positive integer", cardId);
    }
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

  public Runes gameGetRunes(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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

  public Rune gameGetRuneDetail(String username, int runeId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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

  public AllSkill gameGetSkills(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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

  public Skill gameGetSkillDetail(String username, int skillId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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

  public MapStageAll gameGetMapStages(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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

  public MapStage gameGetMapStage(String username, int stageId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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

  public MapStageDetail gameGetMapStageDetail(String username, int stageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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

  public UserInfo gameGetUserInfo(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    UserInfo userInfo;
    if(refresh || (userInfo = account.getUserInfo()) == null) {
      UserInfoResponse response = gameDoAction(username, "user.php", "GetUserinfo", null, UserInfoResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      userInfo = response.getData();
      account.setUserInfo(userInfo);
      account.setInviteCount(userInfo.getInviteNum());
      accountService.saveAccount(account);
      archiveService.addNickname(profiles.get(username).getServerName(), userInfo.getNickName());
    }
    return userInfo;
  }

  public UserCards gameGetUserCards(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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

  public CardGroup gameGetCardGroup(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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

  public UserMapStages gameGetUserMapStages(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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

  public UserMapStage gameGetUserMapStage(String username, int userMapStageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserMapStages stages = gameGetUserMapStages(username, false);
    return stages.get(userMapStageDetailId);
  }

  public UserChip gameGetUserChip(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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

  private void processBonus(MkbAccount account, String... bonuses) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    for(String bonus : bonuses) {
      String[] pair = bonus.split("_");
      String key = pair[0].toLowerCase();
      int value = Integer.parseInt(pair[1]);
      if(key.contains("exp")) {
        account.addExp(value);
        Log.info("Account {} {} has obtained {} exp", account.getUsername(), account.getNickname(), value);
      } else if(key.contains("coin")) {
        account.addGold(value);
        Log.info("Account {} {} has obtained {} coins", account.getUsername(), account.getNickname(), value);
      } else if(key.contains("card")) {
        account.addNewCard(value);
        Log.info("Account {} {} has obtained card {} {}", account.getUsername(), account.getNickname(), value, gameGetCardDetail(account.getUsername(), value).getCardName());
      } else if(key.contains("chip")) {
        account.addNewChip(value);
        Log.info("Account {} {} has obtained chip {}", account.getUsername(), account.getNickname(), value);
      } else {
        Log.warn("Account {} {} has obtained unknown reward {}", account.getUsername(), account.getNickname(), bonus);
      }
    }
  }

  private void processBattleMapResult(MkbAccount account, BattleMap result, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    BattleMapExtData ext = result.getExtData();
    if(ext != null) {
      account.setLevel(ext.getUserLevel());
      if(ext.getStarUp() > 0) {
        account.conquerMapStage(mapStageDetailId);
        Log.info("Account {} {} has conquered new difficulty level at {} {}", account.getUsername(), account.getNickname(), mapStageDetailId, gameGetMapStageDetail(account.getUsername(), mapStageDetailId).getName());
      }
      processBonus(account, ext.getBonus());
      processBonus(account, ext.getFirstBonusWin());
    }
  }

  private void processBattleMazeResult(MkbAccount account, BattleMaze result, int mapStageId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    BattleMazeExtData ext = result.getExtData();
    if(ext != null) {
      BattleMazeExtData.User user = ext.getUser();
      account.setLevel(user.getLevel());
      account.addExp(user.getExp());

      BattleMazeExtData.Clear clear = ext.getClear();
      if(clear.getIsClear() > 0) {
        account.clearMaze(mapStageId);
        int coins = clear.getCoins();
        account.addGold(coins);
        int card = clear.getCardId();
        Log.info("Account {} {} has obtained {} coins and card {} {} as clear maze reward", account.getUsername(), account.getNickname(), coins, card, gameGetCardDetail(account.getUsername(), card).getCardName());
      }

      BattleMazeExtData.Award award = ext.getAward();
      int coins = award.getCoins();
      int exp = award.getExp();
      int card = award.getCardId();
      account.addGold(coins);
      account.addExp(exp);
      if(card > 0) {
        account.addNewCard(award.getCardId());
        Log.info("Account {} {} has obtained {} coins {} exp and card {} {} as maze battle reward", account.getUsername(), account.getNickname(), coins, exp, card, gameGetCardDetail(account.getUsername(), card).getCardName());
      } else {
        Log.info("Account {} {} has obtained {} coins and {} exp as maze battle reward", account.getUsername(), account.getNickname(), coins, exp, card);
      }
      int chip = award.getChip();
      if(chip > 0) {
        account.addNewChip(chip);
        Log.info("Account {} {} has obtained chip fragment {}", account.getUsername(), account.getNickname(), chip);
      }
    }
  }

  public Level gameFindLevel(String username, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MapStageDetail stage = gameGetMapStageDetail(username, mapStageDetailId);
    UserMapStage userStage = gameGetUserMapStage(username, mapStageDetailId);
    List<Level> levels = stage.getLevels();
    int finished = userStage.getFinishedStage();
    if(finished >= levels.size()) {
      finished = levels.size() - 1;
    }
    return levels.get(finished);
  }

  public BattleMap gameMapBattleAuto(String username, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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
    processBattleMapResult(account, result, mapStageDetailId);
    return result;
  }

  public BattleMaze gameMazeBattleAuto(String username, int mapStageId, int layer, int itemIndex) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    Log.debug("{} {} is starting maze battle against maze {}, layer {}, item {}", username, account.getNickname(), mapStageId, layer, itemIndex);
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
      if(response.invalidMazeLayer()) {
        return null;
      }
      throw new UnknownErrorException();
    }
    account.useEnergy(MazeInfo.EnergyExpend);
    BattleMaze result = response.getData();
    processBattleMazeResult(account, result, mapStageId);
    return result;
  }

  public MazeInfo gameGetMazeLayer(String username, int mapStageId, int layer) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mapStageId));
    params.put("Layer", Integer.toString(layer));
    MazeInfoResponse response = gameDoAction(username, "maze.php", "Info", params, MazeInfoResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public MazeShow gameGetMaze(String username, int mapStageId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mapStageId));
    MazeShowResponse response = gameDoAction(username, "maze.php", "Show", params, MazeShowResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public boolean gameResetMaze(String username, int mapStageId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mapStageId));
    MazeResetResponse response = gameDoAction(username, "maze.php", "Reset", params, MazeResetResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public boolean gameAcceptStageClearReward(String username, int mapStageId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mapStageId));
    AwardClearResponse response = gameDoAction(username, "mapstage.php", "AwardClear", params, AwardClearResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public Explore gameExplore(String username, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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

  public Thieves gameGetThieves(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    ThievesResponse response = gameDoAction(username, "arena.php", "GetThieves", null, ThievesResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public GoodsList gameGetGoods(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    GoodsResponse response = gameDoAction(username, "shop.php", "GetGoods", null, GoodsResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public Streng gameUpgradeCard(String username, long targetUserCardId, long... sourceUserCardIds) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    StringBuilder sb = new StringBuilder();
    for(long sourceId : sourceUserCardIds) {
      if(sb.length() > 0) {
        sb.append("_");
      }
      sb.append(sourceId);
    }
    params.put("UserCardId2", sb.toString());
    params.put("UserCardId1", Long.toString(targetUserCardId));
    StrengResponse response = gameDoAction(username, "streng.php", "Card", params, StrengResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

}
