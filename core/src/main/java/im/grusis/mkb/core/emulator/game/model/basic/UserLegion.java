package im.grusis.mkb.core.emulator.game.model.basic;

/**
 * User: Mothership
 * Date: 13-6-12
 * Time: 下午11:22
 */
public class UserLegion extends MkbObject {
  private int LegionId;
  private String LegionName;
  private int StrengExpAdd;
  private int Duty;

  public int getLegionId() {
    return LegionId;
  }

  public String getLegionName() {
    return LegionName;
  }

  public int getStrengExpAdd() {
    return StrengExpAdd;
  }

  public int getDuty() {
    return Duty;
  }
}
