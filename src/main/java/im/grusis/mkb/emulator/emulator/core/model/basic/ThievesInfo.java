package im.grusis.mkb.emulator.emulator.core.model.basic;

/**
 * User: Mothership
 * Date: 13-6-9
 * Time: 上午12:19
 */
public class ThievesInfo {
  private long Uid;
  private String NickName;
  private int Avatar;
  private int Sex;
  private long Time;
  private int Status;  // 1 = active, 2 = finished
  private long[] Attackers;
  private long[] Awards;
  private int HPCount;
  private int HPCurrent;
  private int Type;
  private long UserThievesId;
  private int Round;
  private int MaxAttack;
  private long MaxAttackUid;
  private String MaxAttackName;
  private long LastAttackerUid;
  private String LastAttackerName;
  private long FleeTime;

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

  public long getTime() {
    return Time;
  }

  public void setTime(long time) {
    Time = time;
  }

  public int getStatus() {
    return Status;
  }

  public void setStatus(int status) {
    Status = status;
  }

  public long[] getAttackers() {
    return Attackers;
  }

  public void setAttackers(long[] attackers) {
    Attackers = attackers;
  }

  public long[] getAwards() {
    return Awards;
  }

  public void setAwards(long[] awards) {
    Awards = awards;
  }

  public int getHPCount() {
    return HPCount;
  }

  public void setHPCount(int HPCount) {
    this.HPCount = HPCount;
  }

  public int getHPCurrent() {
    return HPCurrent;
  }

  public void setHPCurrent(int HPCurrent) {
    this.HPCurrent = HPCurrent;
  }

  public int getType() {
    return Type;
  }

  public void setType(int type) {
    Type = type;
  }

  public long getUserThievesId() {
    return UserThievesId;
  }

  public void setUserThievesId(long userThievesId) {
    UserThievesId = userThievesId;
  }

  public int getRound() {
    return Round;
  }

  public void setRound(int round) {
    Round = round;
  }

  public int getMaxAttack() {
    return MaxAttack;
  }

  public void setMaxAttack(int maxAttack) {
    MaxAttack = maxAttack;
  }

  public long getMaxAttackUid() {
    return MaxAttackUid;
  }

  public void setMaxAttackUid(long maxAttackUid) {
    MaxAttackUid = maxAttackUid;
  }

  public String getMaxAttackName() {
    return MaxAttackName;
  }

  public void setMaxAttackName(String maxAttackName) {
    MaxAttackName = maxAttackName;
  }

  public long getLastAttackerUid() {
    return LastAttackerUid;
  }

  public void setLastAttackerUid(long lastAttackerUid) {
    LastAttackerUid = lastAttackerUid;
  }

  public String getLastAttackerName() {
    return LastAttackerName;
  }

  public void setLastAttackerName(String lastAttackerName) {
    LastAttackerName = lastAttackerName;
  }

  public long getFleeTime() {
    return FleeTime;
  }

  public void setFleeTime(long fleeTime) {
    FleeTime = fleeTime;
  }
}
