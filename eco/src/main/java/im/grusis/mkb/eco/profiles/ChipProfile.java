package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:10
 */
public class ChipProfile extends Profile<ChipProfile> {

  public static final String Chip = "chip.";
  public static final String FreeFight = "freeFight";
  public static final boolean FreeFightDefault = true;

  private boolean freeFight;

  public ChipProfile(Environment environment, String root, ChipProfile defaultProfile) {
    super(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, ChipProfile defaultProfile) {
    setFreeFight(environment.getProperty(root + Chip + FreeFight, Boolean.class, defaultProfile == null ? FreeFightDefault : defaultProfile.isFreeFight()));
  }

  public boolean isFreeFight() {
    return freeFight;
  }

  public void setFreeFight(boolean freeFight) {
    this.freeFight = freeFight;
  }
}