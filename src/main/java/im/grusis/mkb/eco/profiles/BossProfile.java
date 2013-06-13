package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:11
 */
public class BossProfile extends Profile<BossProfile> {

  public static final String Prefix = "boss.";
  public static final String Fight = "fight";
  public static final String Adaptive = "adaptive";

  private boolean fight;
  private boolean adaptive;

  public BossProfile(Environment environment, String root, BossProfile defaultProfile) {
    super(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, BossProfile defaultProfile) {
    setFight(environment.getProperty(root + Prefix + Fight, Boolean.class, defaultProfile == null ? null : defaultProfile.isFight()));
    setAdaptive(environment.getProperty(root + Prefix + Adaptive, Boolean.class, defaultProfile == null ? null : defaultProfile.isAdaptive()));
  }

  public boolean isFight() {
    return fight;
  }

  public void setFight(boolean fight) {
    this.fight = fight;
  }

  public boolean isAdaptive() {
    return adaptive;
  }

  public void setAdaptive(boolean adaptive) {
    this.adaptive = adaptive;
  }
}
