package im.grusis.mkb.emulator.emulator.core.model.basic;

/**
 * User: Mothership
 * Date: 13-6-12
 * Time: 下午11:14
 */
public class Member {
  private long Uid;
  private int LegionId;
  private String JoinTime;
  private long Contribute;
  private int duty;
  private long Honor;

  public long getUid() {
    return Uid;
  }

  public void setUid(long uid) {
    Uid = uid;
  }

  public int getLegionId() {
    return LegionId;
  }

  public void setLegionId(int legionId) {
    LegionId = legionId;
  }

  public String getJoinTime() {
    return JoinTime;
  }

  public void setJoinTime(String joinTime) {
    JoinTime = joinTime;
  }

  public long getContribute() {
    return Contribute;
  }

  public void setContribute(long contribute) {
    Contribute = contribute;
  }

  public int getDuty() {
    return duty;
  }

  public void setDuty(int duty) {
    this.duty = duty;
  }

  public long getHonor() {
    return Honor;
  }

  public void setHonor(long honor) {
    Honor = honor;
  }
}
