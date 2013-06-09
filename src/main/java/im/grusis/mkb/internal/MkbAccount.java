package im.grusis.mkb.internal;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import im.grusis.mkb.emulator.emulator.core.model.basic.*;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 下午10:04
 */
public class MkbAccount {
  private String username;
  private String password;
  private String mac;
  private String inviteCode;
  private int inviteCount = 0;
  private String server;

  private int level;
  private long gold;
  private long exp;
  private long diamond;
  private int ticket;
  private List<Integer> newCards;
  private List<Integer> chips;

  private Map<Integer, Long> mazeClearTimes;

  private UserInfo userInfo;
  private long userInfoUpdate;
  private UserCards userCards;
  private long userCardsUpdate;
  private CardGroup cardGroup;
  private long cardGroupUpdate;
  private UserMapStages userMapStages;
  private long userMapStagesUpdate;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getInviteCode() {
    return inviteCode;
  }

  public void setInviteCode(String inviteCode) {
    this.inviteCode = inviteCode;
  }

  public int getInviteCount() {
    return inviteCount;
  }

  public void setInviteCount(int inviteCount) {
    this.inviteCount = inviteCount;
  }

  public String getServer() {
    return server;
  }

  public void setServer(String server) {
    this.server = server;
  }

  public UserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
    level = userInfo.getLevel();
    gold = userInfo.getCoins();
    exp = userInfo.getExp();
    diamond = userInfo.getCash();
    ticket = userInfo.getTicket();
    this.userInfoUpdate = System.currentTimeMillis();
  }

  public CardGroup getCardGroup() {
    return cardGroup;
  }

  public void setCardGroup(CardGroup cardGroup) {
    this.cardGroup = cardGroup;
    this.cardGroupUpdate = System.currentTimeMillis();
  }

  public long getUserInfoUpdate() {
    return userInfoUpdate;
  }

  public long getCardGroupUpdate() {
    return cardGroupUpdate;
  }

  public UserMapStages getUserMapStages() {
    return userMapStages;
  }

  public void setUserMapStages(UserMapStages userMapStages) {
    this.userMapStages = userMapStages;
    this.userMapStagesUpdate = System.currentTimeMillis();
  }

  public long getUserMapStagesUpdate() {
    return userMapStagesUpdate;
  }

  public UserCards getUserCards() {
    return userCards;
  }

  public void setUserCards(UserCards userCards) {
    this.userCards = userCards;
    newCards.clear();
    this.userCardsUpdate = System.currentTimeMillis();
  }

  public long getUserCardsUpdate() {
    return userCardsUpdate;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public long getGold() {
    return gold;
  }

  public void setGold(long gold) {
    this.gold = gold;
  }

  public long getExp() {
    return exp;
  }

  public void setExp(long exp) {
    this.exp = exp;
  }

  public long getDiamond() {
    return diamond;
  }

  public void setDiamond(long diamond) {
    this.diamond = diamond;
  }

  public int getTicket() {
    return ticket;
  }

  public void setTicket(int ticket) {
    this.ticket = ticket;
  }

  public List<Integer> getNewCards() {
    return newCards;
  }

  public void setNewCards(List<Integer> newCards) {
    this.newCards = newCards;
  }

  public Map<Integer, Long> getMazeClearTimes() {
    return mazeClearTimes;
  }

  public void setMazeClearTimes(Map<Integer, Long> mazeClearTimes) {
    this.mazeClearTimes = mazeClearTimes;
  }

  public long getMazeClearTime(int mapStageId) {
    if(mazeClearTimes == null) {
      return 0;
    }
    Long clearTime = mazeClearTimes.get(mapStageId);
    if(clearTime == null) {
      return 0;
    }
    return clearTime;
  }

  public void clearMaze(int mapStageId) {
    if(mazeClearTimes == null) {
      mazeClearTimes = new LinkedHashMap<Integer, Long>();
    }
    mazeClearTimes.put(mapStageId, System.currentTimeMillis());
  }

  public void addGold(long gold) {
    this.gold += gold;
  }

  public void addExp(long exp) {
    this.exp += exp;
  }

  public void addTicket(int ticket) {
    this.ticket += ticket;
  }

  public void addDiamond(int diamond) {
    this.diamond += diamond;
  }

  public void addNewCard(int cardId) {
    newCards.add(cardId);
  }
}
