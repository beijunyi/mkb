package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:08
 */
public class StageProfile extends Profile<StageProfile> {

  public static final String Prefix = "stage.";
  public static final String MaxClear = "maxClear";
  public static final String MaxTry = "maxTry";
  public static final String ClearCounterAttack = "clearCounterAttack";

  private int maxClear;
  private int maxTry;
  private boolean clearCounterAttack;

  public StageProfile(Environment environment, String root, StageProfile defaultProfile) {
    super(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, StageProfile defaultProfile) {
    setMaxClear(environment.getProperty(root + Prefix + MaxClear, Integer.class, defaultProfile == null ? null : defaultProfile.getMaxClear()));
    setMaxTry(environment.getProperty(root + Prefix + MaxTry, Integer.class, defaultProfile == null ? null : defaultProfile.getMaxTry()));
    setClearCounterAttack(environment.getProperty(root + Prefix + ClearCounterAttack, Boolean.class, defaultProfile == null ? null : defaultProfile.isClearCounterAttack()));
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
