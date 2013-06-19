package im.grusis.mkb.core.repository.model;

import java.util.*;

import im.grusis.mkb.core.emulator.TemporaryProfile;
import im.grusis.mkb.core.emulator.game.model.basic.*;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 下午10:04
 */
public class MkbAccount {
  private String username;
  private String password;
  private String mac;
  private String server;

  private List<Integer> newCards;
  private Set<Integer> currentChips;
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

  public void addCoins(long coins) {
    if(userInfo != null) {
      userInfo.addCoins(coins);
    }
  }

  public void addExp(long exp) {
    if(userInfo != null) {
      userInfo.addExp(exp);
    }
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

  public void consumeEnergy(int amount) {
    if(userInfo != null) {
      userInfo.consumeEnergy(amount);
    }
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
    if(userInfo != null) {
      userInfo.addEnergy();
    }
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

  public void removeSender(long fid) {
    energyRecord.remove(fid);
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

  public void setLevel(int level) {
    if(userInfo != null) {
      userInfo.setLevel(level);
    }
  }
}
