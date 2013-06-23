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
  private Map<Long, Integer> energyRecord;
  private Map<Long, BattleRecord> battleRecordMap;

  private TemporaryProfile profile;
  private long lastAction;

  private UserInfo userInfo;
  private long userInfoUpdate;
  private Map<Long, UserCard> userCards;
  private long userCardsUpdate;
  private Map<Long, Group> cardGroup;
  private long cardGroupUpdate;
  private Map<Integer, UserMapStage> userMapStages;
  private long userMapStagesUpdate;
  private Map<Integer, Chip> userChip;
  private long userChipUpdate;
  private Map<Long, Friend> friendMap;
  private long friendsUpdate;
  private UserLegion userLegion;
  private long userLegionUpdate;

  public MkbAccount(String username, String password, String mac) {
    this.username = username;
    this.password = password;
    this.mac = mac;
  }




  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }


  public String getMac() {
    return mac;
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

  public Map<Long, Group> getCardGroup() {
    return cardGroup;
  }

  public void setCardGroup(Map<Long, Group> cardGroup) {
    this.cardGroup = cardGroup;
    this.cardGroupUpdate = System.currentTimeMillis();
  }

  public long getUserInfoUpdate() {
    return userInfoUpdate;
  }

  public long getCardGroupUpdate() {
    return cardGroupUpdate;
  }

  public Map<Integer, UserMapStage> getUserMapStages() {
    return userMapStages;
  }

  public void setUserMapStages(Map<Integer, UserMapStage> userMapStages) {
    this.userMapStages = userMapStages;
    this.userMapStagesUpdate = System.currentTimeMillis();
  }

  public long getUserMapStagesUpdate() {
    return userMapStagesUpdate;
  }

  public Map<Long, UserCard> getUserCards() {
    return userCards;
  }

  public void setUserCards(Map<Long, UserCard> userCards) {
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


  public Map<Integer, Chip> getUserChip() {
    return userChip;
  }

  public void setUserChip(Map<Integer, Chip> userChip) {
    this.userChip = userChip;
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

  public void addChip(int chipId) {
    if(userChip != null) {
      Chip chip = userChip.get(chipId);
      if(chip != null) {
        chip.setNum(chip.getNum() + 1);
      }
    }
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

  public void setUserLegion(UserLegion userLegion) {
    this.userLegion = userLegion;
    userLegionUpdate = System.currentTimeMillis();
  }

  public UserLegion getUserLegion() {
    return userLegion;
  }

  public long getUserLegionUpdate() {
    return userLegionUpdate;
  }

  public void setLevel(int level) {
    if(userInfo != null) {
      userInfo.setLevel(level);
    }
  }

  public Map<Long, BattleRecord> getBattleRecordMap() {
    return battleRecordMap;
  }

  public void battle(long uid, boolean win) {
    if(battleRecordMap == null) {
      battleRecordMap = new TreeMap<Long, BattleRecord>();
    }
    BattleRecord battleRecord = battleRecordMap.get(uid);
    if(battleRecord == null) {
      battleRecord = new BattleRecord();
      battleRecordMap.put(uid, battleRecord);
    }
    if(win) {
      battleRecord.win();
    } else {
      battleRecord.lose();
    }
  }

  public void consumeCards(List<Long> userCardIds) {
    if(userCards != null) {
      for(long ucid : userCardIds) {
        userCards.remove(ucid);
      }
    }
  }
}
