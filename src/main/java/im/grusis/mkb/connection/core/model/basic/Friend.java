package im.grusis.mkb.connection.core.model.basic;

/**
 * User: Mothership
 * Date: 13-5-29
 * Time: 下午11:44
 */
public class Friend {
  long Uid;
  String NickName;
  int Level;
  int Avatar;
  int Sex;
  int Win;
  int Lose;
  int Rank;
  String LastLogin;
  String LegionName;
  long Coins;
  int FriendNum;
  int FriendNumMax;
  int FEnergySurplus;
  int FEnergySend;

  public long getUid() {
    return Uid;
  }

  public void setUid(long uid) {
    Uid = uid;
  }

  public String getNickName() {
    return NickName;
  }

  public void setNickName(String nickName) {
    NickName = nickName;
  }

  public int getLevel() {
    return Level;
  }

  public void setLevel(int level) {
    Level = level;
  }

  public int getAvatar() {
    return Avatar;
  }

  public void setAvatar(int avatar) {
    Avatar = avatar;
  }

  public int getSex() {
    return Sex;
  }

  public void setSex(int sex) {
    Sex = sex;
  }

  public int getWin() {
    return Win;
  }

  public void setWin(int win) {
    Win = win;
  }

  public int getLose() {
    return Lose;
  }

  public void setLose(int lose) {
    Lose = lose;
  }

  public int getRank() {
    return Rank;
  }

  public void setRank(int rank) {
    Rank = rank;
  }

  public String getLastLogin() {
    return LastLogin;
  }

  public void setLastLogin(String lastLogin) {
    LastLogin = lastLogin;
  }

  public String getLegionName() {
    return LegionName;
  }

  public void setLegionName(String legionName) {
    LegionName = legionName;
  }

  public long getCoins() {
    return Coins;
  }

  public void setCoins(long coins) {
    Coins = coins;
  }

  public int getFriendNum() {
    return FriendNum;
  }

  public void setFriendNum(int friendNum) {
    FriendNum = friendNum;
  }

  public int getFriendNumMax() {
    return FriendNumMax;
  }

  public void setFriendNumMax(int friendNumMax) {
    FriendNumMax = friendNumMax;
  }

  public int getFEnergySurplus() {
    return FEnergySurplus;
  }

  public void setFEnergySurplus(int FEnergySurplus) {
    this.FEnergySurplus = FEnergySurplus;
  }

  public int getFEnergySend() {
    return FEnergySend;
  }

  public void setFEnergySend(int FEnergySend) {
    this.FEnergySend = FEnergySend;
  }
}
