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
        account = new MkbAccount(username, password, mac);
        archiveService.addUsername(username);
      }
      if(account.getServer() == null) {
        account.setServer(ret.getGS_NAME());
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

  public ServerInformation webGetGameServers() {
    ServerInformation serverInformation = passportRequest(new ServerRequest(), ServerInformationResponse.class).getReturnObjs();
    assetsService.saveAssets(serverInformation.getGAME_SERVER());
    return serverInformation;
  }

  public GameServer webGetGameServerByName(String name) {
    GameServer gameServer = assetsService.findGameServerByName(name);
    if(gameServer == null) {
      webGetGameServers();
      gameServer = assetsService.findGameServerByName(name);
    }
    return gameServer;
  }

  public GameServer webGetGameServerByDescription(String desc) {
    GameServer gameServer = assetsService.findGameServerByDescription(desc);
    if(gameServer == null) {
      webGetGameServers();
      gameServer = assetsService.findGameServerByDescription(desc);
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
    String nickname = gameGetUserInfo(username, false).getNickName();
    Goods goods = gameShopGetGoods(username, goodsId);
    String goodsName = goods.getName();
    Log.info("Account {} {} is purchasing {} {}", username, nickname, goodsId, goodsName);
    int cash = goods.getCash();
    int coins = goods.getCoins();
    int ticket = goods.getTicket();
    String cost = "";
    if(cash > 0) {
      cost += cash + " cash";
    }
    if(coins > 0) {
      if(!cost.isEmpty()) {
        cost += ", ";
      }
      cost += coins + " coins";
    }
    if(ticket > 0) {
      if(!cost.isEmpty()) {
        cost += ", ";
      }
      cost += ticket + " ticket";
    }
    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("GoodsId", Integer.toString(goodsId));
    ShopBuyResponse response = gameDoAction(username, "shop.php", "Buy", paramMap, ShopBuyResponse.class);
    if(response.badRequest()) {
      if(response.noCurrency()) {
        Log.error("Cannot purchase goods {} {} for account {} {} due to insufficient currency. {} costs {}", goodsId, goodsName, username, nickname, goodsName, cost);
      } else {
        throw new UnknownErrorException();
      }
      return null;
    }
    Log.info("Account {} {} has purchased {} {} for {}", username, nickname, goodsId, goodsName, cost);
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

  public Map<Integer, Card> gameGetCards(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<Integer, Card> cards;
    if(refresh || (cards = assetsService.getCardLookup()).isEmpty()) {
      CardGetAllCardResponse response = gameDoAction(username, "card.php", "GetAllCard", null, CardGetAllCardResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      cards = assetsService.saveAssets(response.getData());
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

  public Map<Integer, Rune> gameGetRunes(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<Integer, Rune> runes;
    if(refresh || (runes = assetsService.getRuneLookup()).isEmpty()) {
      RuneGetAllRuneResponse response = gameDoAction(username, "rune.php", "GetAllRune", null, RuneGetAllRuneResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      runes = assetsService.saveAssets(response.getData());
    }
    return runes;
  }

  public Rune gameGetRuneDetail(String username, int runeId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Rune rune = assetsService.findRune(runeId);
    if(rune == null) {
      rune = gameGetRunes(username, true).get(runeId);
      if(rune == null) {
        throw new UnknownErrorException();
      }
    }
    return rune;
  }

  public Map<Integer, Skill> gameGetSkills(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<Integer, Skill> skills;
    if(refresh || (skills = assetsService.getSkillLookup()).isEmpty()) {
      CardGetAllSkillResponse response = gameDoAction(username, "card.php", "GetAllSkill", null, CardGetAllSkillResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      skills = assetsService.saveAssets(response.getData());
    }
    return skills;
  }

  public Skill gameGetSkillDetail(String username, int skillId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Skill skill = assetsService.findSkill(skillId);
    if(skill == null) {
      skill = gameGetSkills(username, true).get(skillId);
      if(skill == null) {
        throw new UnknownErrorException();
      }
    }
    return skill;
  }

  public Map<Integer, MapStage> gameGetMapStages(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<Integer, MapStage> stages;
    if(refresh || (stages = assetsService.getMapStageLookup()).isEmpty()) {
      MapStageGetMapStageAllResponse response = gameDoAction(username, "mapstage.php", "GetMapStageALL&stageNum=" + stageMax, null, MapStageGetMapStageAllResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      stages = assetsService.saveAssets(response.getData());
    }
    return stages;
  }

  public MapStage gameGetMapStage(String username, int stageId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MapStage mapStage = assetsService.findMapStage(stageId);
    if(mapStage == null) {
      mapStage = gameGetMapStages(username, true).get(stageId);
      if(mapStage == null) {
        throw new UnknownErrorException();
      }
    }
    return mapStage;
  }

  public MapStageDetail gameGetMapStageDetail(String username, int stageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MapStageDetail mapStageDetail = assetsService.findMapStageDetail(stageDetailId);
    if(mapStageDetail == null) {
      gameGetMapStages(username, true);
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
      List<UserCard> cardList = response.getData();
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

  public Map<Integer, Chip> gameGetUserChip(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    Map<Integer, Chip> userChip;
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
    String nickname = gameGetUserInfo(username, false).getNickName();
    Log.debug("Processing purchase result {} for account {} {}", result, username, nickname);
    MkbAccount account = accountService.findAccountByUsername(username);
    String[] cardIdArray = result.split("_");
    StringBuilder sb = new StringBuilder();
    for(String id : cardIdArray) {
      if(sb.length() > 0) {
        sb.append(", ");
      }
      int cardId = Integer.parseInt(id);
      account.addNewCard(cardId);
      sb.append(id).append(' ').append(gameGetCardDetail(username, cardId).getCardName());
    }
    Log.warn("Account {} {} has obtained cards {}", username, nickname, sb.toString());
  }

  private void processBonus(String username, String... bonuses) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    String nickname = gameGetUserInfo(username, false).getNickName();
    Log.debug("Processing bonus {} for {} {}", bonuses, username, nickname);
    MkbAccount account = accountService.findAccountByUsername(username);
    for(String bonus : bonuses) {
      String[] pair = bonus.split("_");
      String key = pair[0].toLowerCase();
      int value = Integer.parseInt(pair[1]);
      if(key.contains("exp")) {
        account.addExp(value);
        Log.info("Account {} {} has obtained {} exp", username, nickname, value);
      } else if(key.contains("coin")) {
        account.addCoins(value);
        Log.info("Account {} {} has obtained {} coins", username, nickname, value);
      } else if(key.contains("card")) {
        account.addNewCard(value);
        Log.info("Account {} {} has obtained card {} {}", username, nickname, value, gameGetCardDetail(account.getUsername(), value).getCardName());
      } else if(key.contains("chip")) {
        account.addChip(value);
        Log.info("Account {} {} has obtained chip {}", username, nickname, value);
      } else {
        Log.warn("Account {} {} has obtained unknown reward {}", username, nickname, bonus);
      }
    }
  }

  private void processBattleMapResult(String username, BattleMap result, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    String nickname = gameGetUserInfo(username, false).getNickName();
    BattleMapExtData ext = result.getExtData();
    if(ext != null) {
      account.setLevel(ext.getUserLevel());
      if(ext.getStarUp() > 0) {
        Log.info("Account {} {} has conquered {} {} difficulty {}", username, nickname, mapStageDetailId, gameGetMapStageDetail(account.getUsername(), mapStageDetailId).getName(), gameGetUserMapStage(username, mapStageDetailId, true).getFinishedStage());
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
  }

  private void processBattleMazeResult(String username, BattleNormal result) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    String nickname = gameGetUserInfo(username, false).getNickName();
    BattleNormalExtData ext = result.getExtData();
    if(ext != null) {
      BattleNormalExtData.User user = ext.getUser();
      account.setLevel(user.getLevel());
      account.addExp(user.getExp());

      BattleNormalExtData.Clear clear = ext.getClear();
      if(clear != null && clear.getIsClear() > 0) {
        int coins = clear.getCoins();
        account.addCoins(coins);
        int card = clear.getCardId();
        Log.info("Account {} {} has obtained {} coins and card {} {} as clear maze reward", username, nickname, coins, card, gameGetCardDetail(account.getUsername(), card).getCardName());
      }

      BattleNormalExtData.Award award = ext.getAward();
      int coins = award.getCoins();
      int exp = award.getExp();
      int card = award.getCardId();
      account.addCoins(coins);
      account.addExp(exp);
      if(card > 0) {
        account.addNewCard(award.getCardId());
        Log.info("Account {} {} has obtained {} coins {} exp and card {} {} as maze battle reward", username, nickname, coins, exp, card, gameGetCardDetail(account.getUsername(), card).getCardName());
      } else {
        Log.info("Account {} {} has obtained {} coins and {} exp as maze battle reward", username, nickname, coins, exp, card);
      }
      int chip = award.getChip();
      if(chip > 0) {
        account.addChip(chip);
        Log.info("Account {} {} has obtained chip fragment {}", username, nickname, chip);
      }
    }
  }

  public Level gameFindLevel(String username, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MapStageDetail stage = gameGetMapStageDetail(username, mapStageDetailId);
    UserMapStage userStage = gameGetUserMapStage(username, mapStageDetailId, false);
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
    return result;
  }

  public BattleNormal gameMazeBattleAuto(String username, int mapStageId, int layer, int itemIndex) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    String nickname = gameGetUserInfo(username, false).getNickName();
    Log.debug("{} {} is starting maze battle against maze {}, layer {}, item {}", username, nickname, mapStageId, layer, itemIndex);
    MkbAccount account = accountService.findAccountByUsername(username);
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("manual", Integer.toString(0));
    params.put("MapStageId", Integer.toString(mapStageId));
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
    processBattleMazeResult(username, result);
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

  public Legions gameGetLegions(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    LegionGetLegionsResponse response = gameDoAction(username, "legion.php", "GetLegions", null, LegionGetLegionsResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    MkbAccount account = accountService.findAccountByUsername(username);
    Legions legions = response.getData();
    account.setLegion(legions.getMyLegion());
    return legions;
  }

  public Legion gameGetMyLegion(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    Legion legion;
    if(refresh || (legion = account.getLegion()) == null) {
      legion = gameGetLegions(username).getMyLegion();
    }
    return legion;
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
    String nickname = gameGetUserInfo(username, false).getNickName();
    Log.debug("{} {} is retrieving melee event information", username, nickname);
    MeleeGetInfoResponse response = gameDoAction(username, "melee.php", "GetInfo", null, MeleeGetInfoResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    MeleeInfo info = response.getData();
    Log.info("{} {} has retrieved melee event information", username, nickname);
    return info;
  }

  public MeleeApplyResult gameMeleeApply(String username, int type) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    String nickname = gameGetUserInfo(username, false).getNickName();
    Log.debug("{} {} is applying for a type {} melee event", username, nickname, type);
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
    Log.info("{} {} obtained {} from type {} melee event", username, nickname, sb.toString(), type);
    return result;
  }

  public MeleeCardGroup gameMeleeGetCardGroup(String username, int type) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    String nickname = gameGetUserInfo(username, false).getNickName();
    Log.debug("{} {} is retrieving type {} melee event card group information", username, nickname, type);
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("type", Integer.toString(type));
    params.put("Uid", Long.toString(gameGetUserInfo(username, false).getUid()));
    MeleeGetCardGroupResponse response = gameDoAction(username, "melee.php", "GetCardGroup", params, MeleeGetCardGroupResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    MeleeCardGroup cardGroup = response.getData();
    Log.info("{} {} has retrieved card group information for type {} melee event", username, nickname, type);
    return cardGroup;
  }

  public LegionAttackInfo gameLegionAttackInfo(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    String nickname = gameGetUserInfo(username, false).getNickName();
    Log.debug("Account {} {} is retrieving legion attack info", username, nickname);
    LegionAttackInfoResponse response = gameDoAction(username, "legionattack.php", "info", null, LegionAttackInfoResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    LegionAttackInfo info = response.getData();
    Log.info("Account {} {} has retrieved legion attack info", username, nickname);
    return info;
  }

  public ArenaCompetitors gameArenaGetCompetitors(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    String nickname = gameGetUserInfo(username, false).getNickName();
    Log.debug("Account {} {} is retrieving arena competitor list", username, nickname);
    ArenaGetCompetitorsResponse response = gameDoAction(username, "arena.php", "GetCompetitors", null, ArenaGetCompetitorsResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    ArenaCompetitors competitors = response.getData();
    Log.info("Account {} {} has successfully retrieved arena competitor list", username, nickname);
    return competitors;
  }

  public BattleNormal gameArenaFreeFightAuto(String username, long competitor, boolean forChip) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    String nickname = gameGetUserInfo(username, false).getNickName();
    Log.debug("{} {} is starting arena free fight against {}", username, nickname, competitor);
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
      Log.info("Account {} {} has won the arena free fight against {} {}", username, nickname, competitor, battle.getDefendPlayer().getNickName());
      account.battle(competitor, true);
    } else {
      Log.info("Account {} {} has lost the arena free fight against {} {}", username, nickname, competitor, battle.getDefendPlayer().getNickName());
      account.battle(competitor, false);
    }
    accountService.saveAccount(account);
    return battle;
  }

  public BattleNormal gameArenaRankFight(String username, int rank) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    String nickname = gameGetUserInfo(username, false).getNickName();
    Log.debug("{} {} is starting arena rank fight against competitor ranked {}", username, nickname, rank);
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
      Log.info("Account {} {} has won the arena rank fight against {} {}", username, nickname, uid, defendPlayer.getNickName());
      account.battle(uid, true);
    } else {
      Log.info("Account {} {} has lost the arena rank fight against {} {}", username, nickname, uid, defendPlayer.getNickName());
      account.battle(uid, false);
    }
    accountService.saveAccount(account);
    return battle;
  }

}
