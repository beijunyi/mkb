package im.grusis.mkb.internal;

import java.util.*;

import im.grusis.mkb.emulator.emulator.TemporaryProfile;
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
  private String nickname;
  private long uid;
  private String inviteCode;
  private int inviteCount = 0;
  private String server;

  private int level;
  private long gold;
  private long exp;
  private long diamond;
  private int ticket;
  private int energy;
  private List<Integer> newCards;
  private Set<Integer> currentChips;
  private Map<Integer, Integer> mapStageProgress;
  private Map<Integer, Long> mazeClearTimes;
  private Map<Long, Integer> energyRecord;

  private TemporaryProfile profile;
  private long lastAction;

  private UserInfo userInfo;
  private long userInfoUpdate;
  private UserCards userCards;
  private long userCardsUpdate;
  private CardGroup cardGroup;
  private long cardGroupUpdate;
  private UserMapStages userMapStages;
  private long userMapStagesUpdate;
  private UserChip userChip;
  private long userChipUpdate;
  private Map<Long, Friend> friendMap;
  private long friendsUpdate;
  private Legion legion;
  private long legionUpdate;

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

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
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

  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
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
    energy = userInfo.getEnergy();
    nickname = userInfo.getNickName();
    inviteCode = userInfo.getInviteCode();
    uid = userInfo.getUid();
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
    if(mapStageProgress == null) {
      mapStageProgress = new TreeMap<Integer, Integer>();
    } else {
      mapStageProgress.clear();
    }
    Collection<UserMapStage> stages = userMapStages.values();
    for(UserMapStage stage : stages) {
      mapStageProgress.put(stage.getMapStageId(), stage.getFinishedStage());
    }
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
    if(newCards == null) {
      newCards = new ArrayList<Integer>();
    } else {
      newCards.clear();
    }
    this.userCardsUpdate = System.currentTimeMillis();
  }

  public long getUserCardsUpdate() {
    return userCardsUpdate;
  }


  public UserChip getUserChip() {
    return userChip;
  }

  public void setUserChip(UserChip userChip) {
    this.userChip = userChip;
    if(currentChips == null) {
      currentChips = new TreeSet<Integer>();
    } else {
      currentChips.clear();
    }
    Collection<Chip> chips = userChip.values();
    for(Chip chip : chips) {
      if(chip.getNum() > 0) {
        currentChips.add(chip.getId());
      }
    }
    this.userChipUpdate = System.currentTimeMillis();
  }

  public long getUserChipUpdate() {
    return userChipUpdate;
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
    if(newCards == null) {
      newCards = new ArrayList<Integer>();
    }
    newCards.add(cardId);
  }

  public void addNewChip(int chip) {
    if(currentChips == null) {
      currentChips = new TreeSet<Integer>();
    }
    currentChips.add(chip);
  }

  public Set<Integer> getCurrentChips() {
    return currentChips;
  }

  public Map<Integer, Integer> getMapStageProgress() {
    return mapStageProgress;
  }

  public void useEnergy(int amount) {
    energy -= amount;
  }

  public int getEnergy() {
    return energy;
  }

  public void setEnergy(int energy) {
    this.energy = energy;
  }

  public TemporaryProfile getProfile() {
    return profile;
  }

  public void setProfile(TemporaryProfile profile) {
    this.profile = profile;
  }

  public void updateLastAction() {
    setLastAction(System.currentTimeMillis());
  }

  public long getLastAction() {
    return lastAction;
  }

  public void setLastAction(long lastAction) {
    this.lastAction = lastAction;
  }

  public void conquerMapStage(int mapStageDetailId) {
    if(mapStageProgress == null) {
      mapStageProgress = new LinkedHashMap<Integer, Integer>();
    }
    Integer currentProgress = mapStageProgress.get(mapStageDetailId);
    if(currentProgress == null) {
      currentProgress = 1;
    } else {
      currentProgress++;
    }
    mapStageProgress.put(mapStageDetailId, currentProgress);
  }

  public Map<Long, Integer> getEnergyRecord() {
    return energyRecord;
  }

  public void setEnergyRecord(Map<Long, Integer> energyRecord) {
    this.energyRecord = energyRecord;
  }

  public void acceptEnergyFrom(long fid) {
    if(energyRecord == null) {
      energyRecord = new LinkedHashMap<Long, Integer>();
    }
    Integer count = energyRecord.get(fid);
    if(count == null) {
      count = 1;
    } else {
      count++;
    }
    energyRecord.put(fid, count);
  }

  public Map<Long, Friend> getFriendMap() {
    return friendMap;
  }

  public long getFriendsUpdate() {
    return friendsUpdate;
  }

  public Map<Long, Friend> setFriends(Friends friends) {
    if(friendMap == null) {
      friendMap = new LinkedHashMap<Long, Friend>();
    } else {
      friendMap.clear();
    }
    for(Friend friend : friends.getFriends()) {
      friendMap.put(friend.getUid(), friend);
    }
    friendsUpdate = System.currentTimeMillis();
    return friendMap;
  }

  public Friend getFriend(long fid) {
    return friendMap.get(fid);
  }

  public List<Long> getEnergySenderList() {
    if(energyRecord == null) {
      return Collections.emptyList();
    }
    List<Long> keys = new ArrayList<Long>(energyRecord.keySet());
    Collections.sort(keys, new Comparator<Long>() {
      @Override
      public int compare(Long o1, Long o2) {
        return energyRecord.get(o2).compareTo(energyRecord.get(o1));
      }
    });
    return keys;
  }

  public void setLegion(Legion legion) {
    this.legion = legion;
    legionUpdate = System.currentTimeMillis();
  }

  public Legion getLegion() {
    return legion;
  }

  public long getLegionUpdate() {
    return legionUpdate;
  }
}
