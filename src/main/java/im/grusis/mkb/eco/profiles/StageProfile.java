package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:08
 */
public class StageProfile extends Profile<StageProfile> {

  public static final String Stage = "stage.";
  public static final String MaxClear = "maxClear";
  public static final int MaxClearDefault = 73;
  public static final String MaxTry = "maxTry";
  public static final int MaxTryDefault = 5;
  public static final String ClearCounterAttack = "clearCounterAttack";
  public static final boolean ClearCounterAttackDefault = true;

  private int maxClear;
  private int maxTry;
  private boolean clearCounterAttack;

  public StageProfile(Environment environment, String root, StageProfile defaultProfile) {
    super(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, StageProfile defaultProfile) {
    setMaxClear(environment.getProperty(root + Stage + MaxClear, Integer.class, defaultProfile == null ? MaxClearDefault : defaultProfile.getMaxClear()));
    setMaxTry(environment.getProperty(root + Stage + MaxTry, Integer.class, defaultProfile == null ? MaxTryDefault : defaultProfile.getMaxTry()));
    setClearCounterAttack(environment.getProperty(root + Stage + ClearCounterAttack, Boolean.class, defaultProfile == null ? ClearCounterAttackDefault : defaultProfile.isClearCounterAttack()));
  }

  public int getMaxClear() {
    return maxClear;
  }

  public void setMaxClear(int maxClear) {
    this.maxClear = maxClear;
  }

  public int getMaxTry() {
    return maxTry;
  }

  public void setMaxTry(int maxTry) {
    this.maxTry = maxTry;
  }

  public boolean isClearCounterAttack() {
    return clearCounterAttack;
  }

  public void setClearCounterAttack(boolean clearCounterAttack) {
    this.clearCounterAttack = clearCounterAttack;
  }
}
