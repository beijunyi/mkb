package im.grusis.mkb.core.emulator.game.model.basic;

/**
 * User: Mothership
 * Date: 13-6-15
 * Time: 下午6:22
 */
public class BossUpdate {
  private Boss Boss;
  private int MyHonor;
  private int CanFightTime;
  private int BossFleeTime;

  public Boss getBoss() {
    return Boss;
  }

  public int getMyHonor() {
    return MyHonor;
  }

  public int getCanFightTime() {
    return CanFightTime;
  }

  public int getBossFleeTime() {
    return BossFleeTime;
  }
}
