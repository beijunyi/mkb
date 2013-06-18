package im.grusis.mkb.core.emulator.game.model.basic;

/**
 * User: Mothership
 * Date: 13-6-6
 * Time: 下午10:29
 */
public class UserMapStage {
  private long Uid;
  private int MapStageDetailId;
  private int Type; // 1 = normal, 2 = boss, 0 = secret, 3 = tower
  private int MapStageId;
  private int FinishedStage;
  private String LastFinishedTime;
  private long CounterAttackTime;

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

  public long getCounterAttackTime() {
    return CounterAttackTime;
  }

  public void setCounterAttackTime(long counterAttackTime) {
    CounterAttackTime = counterAttackTime;
  }

  public int getFinishedStage() {
    return FinishedStage;
  }

  public void setFinishedStage(int finishedStage) {
    FinishedStage = finishedStage;
  }
}
