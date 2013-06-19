package im.grusis.mkb.eco.configs;

/**
 * User: Mothership
 * Date: 13-6-15
 * Time: 下午8:03
 */
public class KeepConfig {

  public static final String KeepConfigPrefix = "keep.";
  public static final String KeepMin = "min";
  public static final int KeepMinDefault = 4;
  public static final String KeepUpgrade = "upgrade";
  public static final boolean KeepUpgradeDefault = true;

  private int min;
  private boolean upgrade;

  public KeepConfig() {
  }

  public KeepConfig(int min, boolean upgrade) {
    this.min = min;
    this.upgrade = upgrade;
  }

  public int getMin() {
    return min;
  }

  public boolean isUpgrade() {
    return upgrade;
  }

}