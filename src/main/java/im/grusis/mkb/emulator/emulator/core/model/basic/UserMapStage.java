package im.grusis.mkb.emulator.emulator.core.model.basic;

/**
 * User: Mothership
 * Date: 13-6-6
 * Time: 下午10:29
 */
public class UserMapStage {
  private long Uid;
  private int MapStageDetailId;
  private int Type;
  private int MapStageId;
  private String LastFinishedTime;
  private int CounterAttackTime;

  public long getUid() {
    return Uid;
  }

  public void setUid(long uid) {
    Uid = uid;
  }

  public int getMapStageDetailId() {
    return MapStageDetailId;
  }

  public void setMapStageDetailId(int mapStageDetailId) {
    MapStageDetailId = mapStageDetailId;
  }

  public int getType() {
    return Type;
  }

  public void setType(int type) {
    Type = type;
  }

  public int getMapStageId() {
    return MapStageId;
  }

  public void setMapStageId(int mapStageId) {
    MapStageId = mapStageId;
  }

  public String getLastFinishedTime() {
    return LastFinishedTime;
  }

  public void setLastFinishedTime(String lastFinishedTime) {
    LastFinishedTime = lastFinishedTime;
  }

  public int getCounterAttackTime() {
    return CounterAttackTime;
  }

  public void setCounterAttackTime(int counterAttackTime) {
    CounterAttackTime = counterAttackTime;
  }
}
