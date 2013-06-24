package im.grusis.mkb.core.emulator.game.model.basic;

/**
 * User: Mothership
 * Date: 13-6-6
 * Time: 下午10:29
 */
public class UserMapStage implements Comparable<UserMapStage> {
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

  public int getMapStageDetailId() {
    return MapStageDetailId;
  }

  public int getType() {
    return Type;
  }

  public int getMapStageId() {
    return MapStageId;
  }

  public int getFinishedStage() {
    return FinishedStage;
  }

  public String getLastFinishedTime() {
    return LastFinishedTime;
  }

  @Override
  public int compareTo(UserMapStage o) {
    return Integer.compare(MapStageDetailId, o.getMapStageDetailId());
  }

  public boolean isCounterAttacked() {
    return CounterAttackTime != 0;
  }

  public long getCounterAttackTime() {
    return CounterAttackTime;
  }

  public void clearCounterAttack() {
    CounterAttackTime = 0;
  }

  public int starUp() {
    return ++FinishedStage;
  }
}
