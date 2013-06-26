package im.grusis.mkb.core.emulator;

import java.util.*;

import im.grusis.mkb.core.emulator.game.MkbGame;
import im.grusis.mkb.core.emulator.game.model.basic.*;
import im.grusis.mkb.core.emulator.game.model.response.*;
import im.grusis.mkb.core.emulator.web.MkbWeb;
import im.grusis.mkb.core.emulator.web.model.basic.GameServer;
import im.grusis.mkb.core.emulator.web.model.basic.LoginInformation;
import im.grusis.mkb.core.emulator.web.model.basic.ServerInformation;
import im.grusis.mkb.core.emulator.web.model.request.*;
import im.grusis.mkb.core.emulator.web.model.response.*;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import im.grusis.mkb.core.service.ArchiveService;
import im.grusis.mkb.core.service.AssetsService;
import im.grusis.mkb.core.util.MacAddressHelper;
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
@PropertySource("classpath:/core_emulator.properties")
public class MkbEmulator {

  private static final Logger Log = LoggerFactory.getLogger(MkbEmulator.class);

  @Value("${platform}") private String platform;
  @Value("${language}") private String language;
  @Value("${versionClient}") private String versionClient;
  @Value("${versionBuild}") private String versionBuild;

  @Value("${stageMax}") private String stageMax;

  @Autowired AccountService accountService;
  @Autowired ArchiveService archiveService;
  @Autowired AssetsService assetsService;

  private ClientConnectionManager connectionManager = new PoolingClientConnectionManager();
  private DefaultHttpClient shared = new DefaultHttpClient(connectionManager);
  private Map<String, DefaultHttpClient> httpClients = new HashMap<String, DefaultHttpClient>();
  private List<DefaultHttpClient> freeClients = new ArrayList<DefaultHttpClient>();

  private Map<String, TemporaryProfile> profiles = new HashMap<String, TemporaryProfile>();
  private Map<String, MkbGame> cores = new HashMap<String, MkbGame>();

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

  public MkbGame getMkbCore(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbGame core = cores.get(username);
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
      core = new MkbGame(profile.getHost(), getHttpClient(username), platform, language, versionClient, versionBuild);
      cores.put(username, core);
    }
    return core;
  }

  public <T extends ReturnTemplate> T passportRequest(PassportRequest<T> passportRequest, Class<T> clazz) {
    MkbWeb helper = new MkbWeb(shared);
    helper.requestEncryptKey();
    helper.proposeCounterKey();
    return helper.sendRequest(passportRequest, clazz);
  }

  public TemporaryProfile webLogin(String username) throws WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    if(account == null) {
      Log.error("Cannot login account {}. There is no credential record in the database for this account.", username);
      throw new WrongCredentialException();
    }
    return webLogin(username, account.getPassword(), account.getMac());
  }

  public TemporaryProfile webLogin(String username, String password) throws WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    String mac;
    if(account != null) {
      mac = account.getMac();
    } else {
      mac = MacAddressHelper.getMacAddress();
    }
    return webLogin(username, password, mac);
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
      TemporaryProfile profile = new TemporaryProfile(ret.getGS_DESC(), ret.getGS_IP(), username, password, ret.getU_ID(), mac, ret.getKey(), ret.getTimestamp());
      profiles.put(username, profile);
      MkbAccount account = accountService.findAccountByUsername(username);
      if(account == null) {
        account = new MkbAccount(username, password, mac);
        archiveService.addUsername(username);
      }
      if(account.getServer() == null) {
        account.setServer(ret.getGS_DESC());
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
    MkbAccount account = new MkbAccount(username, password, mac);
    accountService.saveAccount(account);
    archiveService.addUsername(username);
    return true;
  }

  public Map<String, GameServer> webGetGameServers(boolean refresh) {
    Map<String, GameServer> servers;
    if(refresh || (servers = assetsService.getGameServerLookup()).isEmpty()) {
      ServerInformation serverInformation = passportRequest(new ServerRequest(), ServerInformationResponse.class).getReturnObjs();
      servers = assetsService.saveAssets(serverInformation.getGAME_SERVER());
    }
    return servers;
  }

  public GameServer webGetGameServerByDescription(String desc) {
    GameServer gameServer = assetsService.findGameServer(desc);
    if(gameServer == null) {
      gameServer = webGetGameServers(true).get(desc);
    }
    return gameServer;
  }

  public String gameDoAction(String username, String service, String action, Map<String, String> paramMap) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbGame core = getMkbCore(username);
    String result = core.doAction(service, action, paramMap);
    accountService.findAccountByUsername(username).updateLastAction();
    return result;
  }

  public <T extends GameData> T gameDoAction(String username, String service, String action, Map<String, String> paramMap, Class<T> clazz) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    String responseString = gameDoAction(username, service, action, paramMap);
    T response = GameDataFactory.getGameData(responseString, clazz);
    if(response.badRequest()) {
      if(response.disconnected()) {
        if(action.equals("PassportLogin")) {
          return response;
        }
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
    LoginPassportLoginResponse response = gameDoAction(username, "login.php", "PassportLogin", paramMap, LoginPassportLoginResponse.class);
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
    UserEditNickNameResponse response = gameDoAction(username, "user.php", "EditNickName", paramMap, UserEditNickNameResponse.class);
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

  public String gamePurchase(String username, int goodsId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    Goods goods = gameShopGetGoods(username, goodsId);
    Log.info("{} is purchasing {}", userInfo, goods);

    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("GoodsId", Integer.toString(goodsId));
    ShopBuyResponse response = gameDoAction(username, "shop.php", "Buy", paramMap, ShopBuyResponse.class);
    if(response.badRequest()) {
      if(response.noCurrency()) {
        Log.error("{} cannot purchase {} due to insufficient currency. Require {}", userInfo, goods, goods.getCostDescription());
      } else {
        throw new UnknownErrorException();
      }
      return null;
    }
    Log.info("{} has successfully purchased {} for {}", userInfo, goods, goods.getCostDescription());
    return response.getData();
  }

  public boolean gameSkipTutorial(String username, int type, int stage) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("FreshStep", type + "_" + stage);
    UserEditFreshResponse response = gameDoAction(username, "user.php", "EditFresh", paramMap, UserEditFreshResponse.class);
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

  public boolean gameFetchSalary(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserGetUserSalaryResponse response = gameDoAction(username, "user.php", "GetUserSalary", null, UserGetUserSalaryResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public boolean gameAcceptSalary(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserAwardSalaryResponse response = gameDoAction(username, "user.php", "AwardSalary", null, UserAwardSalaryResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public Map<Integer, CardDef> gameGetCards(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<Integer, CardDef> cards;
    if(refresh || (cards = assetsService.getCardLookup()).isEmpty()) {
      CardGetAllCardResponse response = gameDoAction(username, "card.php", "GetAllCard", null, CardGetAllCardResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      cards = assetsService.saveAssets(response.getData());
    }
    return cards;
  }

  public CardDef gameGetCardDetail(String username, int cardId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    if(cardId <= 0) {
      Log.error("{} is an invalid card id. Card id must be positive integer", cardId);
    }
    CardDef card = assetsService.findCard(cardId);
    if(card == null) {
      gameGetCards(username, true);
      card = assetsService.findCard(cardId);
      if(card == null) {
        throw new UnknownErrorException();
      }
    }
    return card;
  }

  public Map<Integer, RuneDef> gameGetRunes(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<Integer, RuneDef> runes;
    if(refresh || (runes = assetsService.getRuneLookup()).isEmpty()) {
      RuneGetAllRuneResponse response = gameDoAction(username, "rune.php", "GetAllRune", null, RuneGetAllRuneResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      runes = assetsService.saveAssets(response.getData());
    }
    return runes;
  }

  public RuneDef gameGetRuneDetail(String username, int runeId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    RuneDef rune = assetsService.findRune(runeId);
    if(rune == null) {
      rune = gameGetRunes(username, true).get(runeId);
      if(rune == null) {
        throw new UnknownErrorException();
      }
    }
    return rune;
  }

  public Map<Integer, SkillDef> gameGetSkills(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<Integer, SkillDef> skills;
    if(refresh || (skills = assetsService.getSkillLookup()).isEmpty()) {
      CardGetAllSkillResponse response = gameDoAction(username, "card.php", "GetAllSkill", null, CardGetAllSkillResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      skills = assetsService.saveAssets(response.getData());
    }
    return skills;
  }

  public SkillDef gameGetSkillDetail(String username, int skillId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    SkillDef skill = assetsService.findSkill(skillId);
    if(skill == null) {
      skill = gameGetSkills(username, true).get(skillId);
      if(skill == null) {
        throw new UnknownErrorException();
      }
    }
    return skill;
  }

  public Map<Integer, MapDef> gameGetMapDefs(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<Integer, MapDef> stages;
    if(refresh || (stages = assetsService.getMapStageLookup()).isEmpty()) {
      MapStageGetMapStageAllResponse response = gameDoAction(username, "mapstage.php", "GetMapStageALL&stageNum=" + stageMax, null, MapStageGetMapStageAllResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      stages = assetsService.saveAssets(response.getData());
    }
    return stages;
  }

  public MapDef gameGetMapDef(String username, int stageId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MapDef mapStage = assetsService.findMapStage(stageId);
    if(mapStage == null) {
      mapStage = gameGetMapDefs(username, true).get(stageId);
      if(mapStage == null) {
        throw new UnknownErrorException();
      }
    }
    return mapStage;
  }

  public MapStageDef gameGetMapStageDef(String username, int stageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MapStageDef mapStageDef = assetsService.findMapStageDetail(stageDetailId);
    if(mapStageDef == null) {
      gameGetMapDefs(username, true);
      mapStageDef = assetsService.findMapStageDetail(stageDetailId);
      if(mapStageDef == null) {
        throw new UnknownErrorException();
      }
    }
    return mapStageDef;
  }

  public UserInfo gameGetUserInfo(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    UserInfo userInfo;
    if(refresh || (userInfo = account.getUserInfo()) == null) {
      UserGetUserInfoResponse response = gameDoAction(username, "user.php", "GetUserinfo", null, UserGetUserInfoResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      userInfo = response.getData();
      account.setUserInfo(userInfo);
      accountService.saveAccount(account);
      archiveService.addNickname(profiles.get(username).getServerName(), userInfo.getNickName());
    }
    return userInfo;
  }

  public Map<Long, UserCard> gameGetUserCards(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    Map<Long, UserCard> cards;
    if(refresh || (cards = account.getUserCards()) == null) {
      CardGetUserCardsResponse response = gameDoAction(username, "card.php", "GetUserCards", null, CardGetUserCardsResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      List<UserCard> cardList = response.getData().getCards();
      cards = new LinkedHashMap<Long, UserCard>();
      for(UserCard card : cardList) {
        cards.put(card.getUserCardId(), card);
      }
      account.setUserCards(cards);
      accountService.saveAccount(account);
    }
    return cards;
  }

  public Map<Long, Group> gameGetCardGroup(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    Map<Long, Group> cardGroup;
    if(refresh || (cardGroup = account.getCardGroup()) == null) {
      CardGetCardGroupResponse response = gameDoAction(username, "card.php", "GetCardGroup", null, CardGetCardGroupResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      CardGroup cardGroups = response.getData();
      cardGroup = new TreeMap<Long, Group>();
      for(Group g : cardGroups.getGroups()) {
        cardGroup.put(g.getGroupId(), g);
      }
      account.setCardGroup(cardGroup);
      accountService.saveAccount(account);
    }
    return cardGroup;
  }

  public Map<Integer, UserMapStage> gameGetUserMapStages(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    Map<Integer, UserMapStage> stages;
    if(refresh || (stages = account.getUserMapStages()) == null) {
      MapStageGetUserMapStagesResponse response = gameDoAction(username, "mapstage.php", "GetUserMapStages", null, MapStageGetUserMapStagesResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      stages = response.getData();
      account.setUserMapStages(stages);
      accountService.saveAccount(account);
    }

    return stages;
  }

  public UserMapStage gameGetUserMapStage(String username, int userMapStageDetailId, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<Integer, UserMapStage> stages = gameGetUserMapStages(username, refresh);
    return stages.get(userMapStageDetailId);
  }

  public ChipPuzzle gameGetUserChip(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    ChipPuzzle userChip;
    if(refresh || (userChip = account.getUserChip()) == null) {
      ChipGetUserChipResponse response = gameDoAction(username, "chip.php", "GetUserChip", null, ChipGetUserChipResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      userChip = response.getData();
      account.setUserChip(userChip);
      accountService.saveAccount(account);
    }
    return userChip;
  }

  private void processGoodsPurchaseResult(String username, String result) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    Log.debug("Processing purchase result {} for {}", result, userInfo);
    MkbAccount account = accountService.findAccountByUsername(username);
    String[] cardIdArray = result.split("_");
    StringBuilder sb = new StringBuilder();
    for(String id : cardIdArray) {
      if(sb.length() > 0) {
        sb.append(", ");
      }
      int cardId = Integer.parseInt(id);
      account.addNewCard(cardId);
      sb.append(gameGetCardDetail(username, cardId));
    }
    Log.info("{} has obtained {}", userInfo, sb.toString());
  }

  private void processBonus(String username, String... bonuses) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    Log.debug("Processing bonus {} for {}", bonuses, userInfo);
    MkbAccount account = accountService.findAccountByUsername(username);
    for(String bonus : bonuses) {
      String[] pair = bonus.split("_");
      String key = pair[0].toLowerCase();
      int value = Integer.parseInt(pair[1]);
      if(key.contains("exp")) {
        userInfo.addExp(value);
        Log.info("{} has obtained {} exp", userInfo, value);
      } else if(key.contains("coin")) {
        userInfo.addCoins(value);
        Log.info("{} has obtained {} coins", userInfo, value);
      } else if(key.contains("card")) {
        account.addNewCard(value);
        Log.info("{} has obtained {}", userInfo, gameGetCardDetail(username, value));
      } else if(key.contains("chip")) {
        ChipPuzzle chipPuzzle = gameGetUserChip(username, false);
        if(chipPuzzle.addChip(value)) {
          userInfo.addTicket();
          Log.info("{} has obtained chip fragment {} finishing chip collection and gain 1 ticket", userInfo, value);
        } else {
          Log.info("{} has obtained chip fragment {}", userInfo, value);
        }
      } else {
        Log.warn("{} has obtained *** UNKNOWN REWARD *** {}", userInfo, bonus);
      }
    }
  }

  private UserMapStage processBattleMapResult(String username, BattleMap result, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    MapStageDef mapStageDef = gameGetMapStageDef(username, mapStageDetailId);
    UserMapStage userMapStage = gameGetUserMapStage(username, mapStageDetailId, false);
    if(result.win()) {
      userMapStage.clearCounterAttack();
    }
    BattleMapExtData ext = result.getExtData();
    if(ext != null) {
      userInfo.setLevel(ext.getUserLevel());
      if(ext.getStarUp() > 0) {
        Log.info("{} has conquered {} difficulty {}", userInfo, mapStageDef, userMapStage.starUp());
      }
      String[] bonuses = ext.getBonus();
      if(bonuses != null) {
        processBonus(username, bonuses);
      }
      String[] firstBonuses = ext.getFirstBonusWin();
      if(firstBonuses != null) {
        processBonus(username, firstBonuses);
      }
    }
    return userMapStage;
  }

  private void processBattleResult(String username, BattleNormal result) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    BattleNormalExtData ext = result.getExtData();
    if(ext != null) {
      BattleNormalExtData.User user = ext.getUser();
      userInfo.setLevel(user.getLevel());

      BattleNormalExtData.Award award = ext.getAward();
      int coins = award.getCoins();
      int exp = award.getExp();
      userInfo.addCoins(coins);
      userInfo.addExp(exp);
      Log.info("{} has obtained {} coins and {} exp as maze battle reward", userInfo, coins, exp);
      int card = award.getCardId();
      int chip = award.getChip();
      if(card > 0 || chip > 0) {
        MkbAccount account = accountService.findAccountByUsername(username);
        if(card > 0) {
          account.addNewCard(award.getCardId());
          Log.info("{} has obtained {} as maze battle reward", userInfo, gameGetCardDetail(username, card));
        }
        if(chip > 0) {
          ChipPuzzle chipPuzzle = gameGetUserChip(username, false);
          if(chipPuzzle.addChip(chip)) {
            userInfo.addTicket();
            Log.info("{} has obtained chip fragment {} finishing chip collection and gain 1 ticket", userInfo, chip);
          } else {
            Log.info("{} has obtained chip fragment {}", userInfo, chip);
          }
        }
      }
    }
  }

  private MazeStatus processBattleMazeResult(String username, BattleNormal result, int mazeId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    BattleNormalExtData ext = result.getExtData();
    MazeStatus mazeStatus = gameGetMazeStatus(username, mazeId, false);
    if(ext != null) {
      BattleNormalExtData.Clear clear = ext.getClear();
      if(clear != null && clear.getIsClear() > 0) {
        int coins = clear.getCoins();
        userInfo.addCoins(coins);
        int card = clear.getCardId();
        MkbAccount account = accountService.findAccountByUsername(username);
        account.addNewCard(card);
        mazeStatus.clear();
        account.setMazeStatus(mazeId, mazeStatus);
        accountService.saveAccount(account);
        Log.info("{} has cleared maze {} and obtained {} coins and {} as clear maze reward", userInfo, mazeId, coins, gameGetCardDetail(username, card));
      }
    }
    processBattleResult(username, result);
    return mazeStatus;
  }

  public Level gameFindLevel(String username, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MapStageDef stage = gameGetMapStageDef(username, mapStageDetailId);
    UserMapStage userStage = gameGetUserMapStage(username, mapStageDetailId, false);
    List<Level> levels = stage.getLevels();
    int finished = userStage.getFinishedStage();
    if(finished >= levels.size()) {
      finished = levels.size() - 1;
    }
    return levels.get(finished);
  }

  public UserMapStage gameMapBattleAuto(String username, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageDetailId", Integer.toString(mapStageDetailId));
    params.put("isManual", Integer.toString(0));
    MapStageEditUserMapStagesResponse response = gameDoAction(username, "mapstage.php", "EditUserMapStages", params, MapStageEditUserMapStagesResponse.class);
    if(response.badRequest()) {
      if(response.noEnergy()) {
        return null;
      }
      throw new UnknownErrorException();
    }
    MkbAccount account = accountService.findAccountByUsername(username);
    account.consumeEnergy(gameFindLevel(username, mapStageDetailId).getEnergyExpend());
    BattleMap result = response.getData();
    processBattleMapResult(username, result, mapStageDetailId);
    return gameGetUserMapStage(username, mapStageDetailId, false);
  }

  public BattleNormal gameMazeBattleAuto(String username, int mazeId, int layer, int itemIndex) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    Log.debug("{} is starting maze battle against maze {}, layer {}, item {}", userInfo, mazeId, layer, itemIndex);
    MkbAccount account = accountService.findAccountByUsername(username);
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("manual", Integer.toString(0));
    params.put("MapStageId", Integer.toString(mazeId));
    params.put("Layer", Integer.toString(layer));
    params.put("ItemIndex", Integer.toString(itemIndex));
    MazeBattleResponse response = gameDoAction(username, "maze.php", "Battle", params, MazeBattleResponse.class);
    if(response.badRequest()) {
      if(response.noEnergy()) {
        return null;
      }
      if(response.invalidMazeLayer()) {
        return null;
      }
      throw new UnknownErrorException();
    }
    account.consumeEnergy(MazeInfo.EnergyExpend);
    BattleNormal result = response.getData();
    processBattleMazeResult(username, result, mazeId);
    return result;
  }

  public MazeInfo gameGetMazeLayer(String username, int mazeId, int level) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    Log.debug("{} is retrieving maze level status for maze {} level {}", userInfo, mazeId, level);
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mazeId));
    params.put("Layer", Integer.toString(level));
    MazeInfoResponse response = gameDoAction(username, "maze.php", "Info", params, MazeInfoResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    gameGetMazeStatus(username, mazeId, false).setLayer(level);
    Log.info("{} has successfully retrieved maze level status for maze {} level {}", userInfo, mazeId, level);
    return response.getData();
  }

  public MazeStatus gameGetMazeStatus(String username, int mazeId, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    Log.debug("{} is retrieving maze status for maze {}", userInfo, mazeId);
    MazeStatus mazeStatus;
    MkbAccount account = accountService.findAccountByUsername(username);
    if(refresh || (mazeStatus = account.getMazeStatus(mazeId)) == null) {
      Map<String, String> params = new LinkedHashMap<String, String>();
      params.put("MapStageId", Integer.toString(mazeId));
      MazeShowResponse response = gameDoAction(username, "maze.php", "Show", params, MazeShowResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      mazeStatus = response.getData();
    }
    account.setMazeStatus(mazeId, mazeStatus);
    accountService.saveAccount(account);
    Log.info("{} has successfully retrieved maze status for maze {}", userInfo, mazeId);
    return mazeStatus;
  }

  public MazeStatus gameResetMaze(String username, int mazeId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    Log.debug("{} is resetting maze {}", userInfo, mazeId);
    MazeStatus mazeStatus = gameGetMazeStatus(username, mazeId, false);
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mazeId));
    MazeResetResponse response = gameDoAction(username, "maze.php", "Reset", params, MazeResetResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    if(!mazeStatus.allowFreeReset()) {
      userInfo.consumeCash(mazeStatus.getResetCash());
    }
    return gameGetMazeStatus(username, mazeId, true);
  }

  public boolean gameAcceptStageClearReward(String username, int mapStageId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mapStageId));
    MapStageAwardClearResponse response = gameDoAction(username, "mapstage.php", "AwardClear", params, MapStageAwardClearResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public Explore gameExplore(String username, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageDetailId", Integer.toString(mapStageDetailId));
    MapStageExploreResponse response = gameDoAction(username, "mapstage.php", "Explore", params, MapStageExploreResponse.class);
    MkbAccount account = accountService.findAccountByUsername(username);
    account.consumeEnergy(gameFindLevel(username, mapStageDetailId).getEnergyExplore());
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public Map<Long, ThievesInfo> gameGetThieves(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    ArenaGetThievesResponse response = gameDoAction(username, "arena.php", "GetThieves", null, ArenaGetThievesResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    Thieves thieves = response.getData();
    Map<Long, ThievesInfo> thiefMap = new LinkedHashMap<Long, ThievesInfo>();
    for(ThievesInfo thief : thieves.getThieves()) {
      thiefMap.put(thief.getUserThievesId(), thief);
    }
    return thiefMap;
  }

  public GoodsList gameShopGetGoodsList(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    GoodsList goodsList;
    if(refresh || (goodsList = assetsService.getGoods()) == null) {
      ShopGetGoodsResponse response = gameDoAction(username, "shop.php", "GetGoods", null, ShopGetGoodsResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      goodsList = response.getData();
      assetsService.saveAssets(goodsList);
    }
    return goodsList;
  }

  public Goods gameShopGetGoods(String username, int goodsId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Goods goods = assetsService.findGoods(goodsId);
    if(goods == null) {
      gameShopGetGoodsList(username, true);
      goods = assetsService.findGoods(goodsId);
      if(goods == null) {
        throw new UnknownErrorException();
      }
    }
    return goods;
  }

  public Streng gameUpgradeCard(String username, long targetUserCardId, List<Long> sourceUserCardIds) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
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
    StrengCardResponse response = gameDoAction(username, "streng.php", "Card", params, StrengCardResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public Map<Long, Friend> gameGetFriends(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    Map<Long, Friend> friends;
    if(refresh || (friends = account.getFriendMap()) == null) {
      FriendGetFriendsResponse response = gameDoAction(username, "friend.php", "GetFriends", null, FriendGetFriendsResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      friends = account.setFriends(response.getData());
      accountService.saveAccount(account);
    }
    return friends;
  }

  public Friend gameGetFriend(String username, long fid) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    return gameGetFriends(username, false).get(fid);
  }

  public FriendApplys gameGetFriendApplies(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    FriendGetFriendApplysResponse response = gameDoAction(username, "friend.php", "GetFriendApplys", null, FriendGetFriendApplysResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public boolean gameDisposeFriendApply(String username, long friendId, boolean accept) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    String type;
    if(accept) {
      type = "agree";
    } else {
      type = "reject";
    }
    params.put("type", type);
    params.put("Fid", Long.toString(friendId));
    FriendDisposeFriendApplyResponse response = gameDoAction(username, "friend.php", "DisposeFriendApply", params, FriendDisposeFriendApplyResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public boolean gameSendEnergy(String username, long fid) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("Fid", Long.toString(fid));
    FEnergySendFEnergyResponse response = gameDoAction(username, "fenergy.php", "SendFEnergy", params, FEnergySendFEnergyResponse.class);
    if(response.badRequest()) {
      if(response.energySendMax()) {
        return false;
      }
      if(response.energyAlreadySent()) {
        return false;
      }
      throw new UnknownErrorException();
    }
    gameGetFriend(username, fid).setFEnergySend(0);
    return true;
  }

  public boolean gameAcceptEnergy(String username, long fid) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("Fid", Long.toString(fid));
    FEnergyGetFEnergyResponse response = gameDoAction(username, "fenergy.php", "GetFEnergy", params, FEnergyGetFEnergyResponse.class);
    if(response.badRequest()) {
      if(response.energyGetMax()) {
        return false;
      }
      if(response.energyOverMax()) {
        return false;
      }
      throw new UnknownErrorException();
    }
    gameGetFriend(username, fid).setFEnergySurplus(0);
    MkbAccount account = accountService.findAccountByUsername(username);
    account.acceptEnergyFrom(fid);
    accountService.saveAccount(account);
    return true;
  }

  public Tech gameGetLegionTechs(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    LegionGetTechResponse response = gameDoAction(username, "legion.php", "GetTech", null, LegionGetTechResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public UserLegion gameGetUserLegion(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    Log.debug("{} is retrieving user legion information", userInfo);
    MkbAccount account = accountService.findAccountByUsername(username);
    UserLegion userLegion;
    if(refresh || (userLegion = account.getUserLegion()) == null) {
      LegionGetUserLegionResponse response = gameDoAction(username, "legion.php", "GetUserLegion", null, LegionGetUserLegionResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      userLegion = response.getData();
      account.setUserLegion(userLegion);
      accountService.saveAccount(account);
    }
    Log.debug("{} has successfully retrieved user legion information", userInfo);
    return userLegion;
  }

  public Legions gameGetLegions(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    Log.debug("{} is retrieving legions information", userInfo);
    LegionGetLegionsResponse response = gameDoAction(username, "legion.php", "GetLegions", null, LegionGetLegionsResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    Legions legions = response.getData();
    Log.debug("{} has successfully retrieved legions information", userInfo);
    return legions;
  }

  public Members gameGetLegionMembers(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    LegionGetMemberResponse response = gameDoAction(username, "legion.php", "GetMember", null, LegionGetMemberResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public BossUpdate gameGetBossUpdate(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    BossGetBossResponse response = gameDoAction(username, "boss.php", "GetBoss", null, BossGetBossResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public BossFight gameBossFight(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    BossFightResponse response = gameDoAction(username, "boss.php", "Fight", null, BossFightResponse.class);
    if(response.badRequest()) {
      if(response.alreadyInQueue()) {
        return null;
      }
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public BattleNormal gameGetBossBattle(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    BossGetFightDataResponse response = gameDoAction(username, "boss.php", "GetFightData", null, BossGetFightDataResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public boolean gameSetMeleeCardGroup(String username, int type, int prizeCardId, int... otherCardId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    StringBuilder sb = new StringBuilder();
    for(int id : otherCardId) {
      if(sb.length() > 0) {
        sb.append('_');
      }
      sb.append(id);
    }
    params.put("PrizeCardId", Integer.toString(prizeCardId));
    params.put("type", Integer.toString(type));
    params.put("OtherCardId", sb.toString());
    MeleeSetCardGroupResponse response = gameDoAction(username, "melee.php", "SetCardGroup", params, MeleeSetCardGroupResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public MeleeInfo gameMeleeGetInfo(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    Log.debug("{} is retrieving melee event information", userInfo);
    MeleeGetInfoResponse response = gameDoAction(username, "melee.php", "GetInfo", null, MeleeGetInfoResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    MeleeInfo info = response.getData();
    Log.info("{} has retrieved melee event information", userInfo);
    return info;
  }

  public MeleeApplyResult gameMeleeApply(String username, int type) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    Log.debug("{} is applying for a type {} melee event", userInfo, type);
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("type", Integer.toString(type));
    MeleeApplyResponse response = gameDoAction(username, "melee.php", "Apply", params, MeleeApplyResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    MeleeApplyResult result = response.getData();
    String[] ids = result.getCardAll().split("_");
    StringBuilder sb = new StringBuilder();
    for(String id : ids) {
      if(sb.length() > 0) {
        sb.append(", ");
      }
      sb.append(id).append(' ').append(gameGetCardDetail(username, Integer.parseInt(id)).getCardName());
    }
    Log.info("{} obtained {} from type {} melee event", userInfo, sb, type);
    return result;
  }

  public MeleeCardGroup gameMeleeGetCardGroup(String username, int type) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    Log.debug("{} is retrieving type {} melee event card group information", userInfo, type);
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("type", Integer.toString(type));
    params.put("Uid", Long.toString(gameGetUserInfo(username, false).getUid()));
    MeleeGetCardGroupResponse response = gameDoAction(username, "melee.php", "GetCardGroup", params, MeleeGetCardGroupResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    MeleeCardGroup cardGroup = response.getData();
    Log.info("{} has retrieved card group information for type {} melee event", userInfo, type);
    return cardGroup;
  }

  public LegionAttackInfo gameLegionAttackInfo(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    Log.debug("{} is retrieving legion attack info", userInfo);
    LegionAttackInfoResponse response = gameDoAction(username, "legionattack.php", "info", null, LegionAttackInfoResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    LegionAttackInfo info = response.getData();
    Log.info("{} has retrieved legion attack info", userInfo);
    return info;
  }

  public ArenaCompetitors gameArenaGetCompetitors(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    Log.debug("{} is retrieving arena competitor list", userInfo);
    ArenaGetCompetitorsResponse response = gameDoAction(username, "arena.php", "GetCompetitors", null, ArenaGetCompetitorsResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    ArenaCompetitors competitors = response.getData();
    Log.info("{} has successfully retrieved arena competitor list", userInfo);
    return competitors;
  }

  public BattleNormal gameArenaFreeFightAuto(String username, long competitor, boolean forChip) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    Log.debug("{} is starting arena free fight against {}", userInfo, competitor);
    Map<String, String> params = new LinkedHashMap<String, String>();
    if(!forChip) {
      params.put("NoChip", Integer.toString(1));
    }
    params.put("isManual", Integer.toString(0));
    params.put("competitor", Long.toString(competitor));
    ArenaFreeFightResponse response = gameDoAction(username, "arena.php", "FreeFight", params, ArenaFreeFightResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    BattleNormal battle = response.getData();
    MkbAccount account = accountService.findAccountByUsername(username);
    if(battle.win()) {
      Log.info("{} has won the arena free fight against {}", userInfo, battle.getDefendPlayer());
      account.battle(competitor, true);
    } else {
      Log.info("{} has lost the arena free fight against {}", userInfo, battle.getDefendPlayer());
      account.battle(competitor, false);
    }
    accountService.saveAccount(account);
    return battle;
  }

  public BattleNormal gameArenaRankFight(String username, int rank) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = gameGetUserInfo(username, false);
    Log.debug("{} is starting arena rank fight against competitor ranked {}", userInfo);
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("CompetitorRank", Integer.toString(rank));
    ArenaFreeFightResponse response = gameDoAction(username, "arena.php", "RankFight", params, ArenaFreeFightResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    BattleNormal battle = response.getData();
    MkbAccount account = accountService.findAccountByUsername(username);
    Player defendPlayer = battle.getDefendPlayer();
    long uid = defendPlayer.getUid();
    if(battle.win()) {
      Log.info("{} has won the arena rank fight against {}", userInfo, defendPlayer);
      account.battle(uid, true);
    } else {
      Log.info("{} has lost the arena rank fight against {}", userInfo, defendPlayer);
      account.battle(uid, false);
    }
    accountService.saveAccount(account);
    return battle;
  }

}
