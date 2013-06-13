package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:09
 */
public class LegionProfile extends Profile<LegionProfile> {

  public static final String Prefix = "legion.";
  public static final String Donate = "donate";

  private long donate;

  public LegionProfile(Environment environment, String root, LegionProfile defaultProfile) {
    super(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, LegionProfile defaultProfile) {
    setDonate(environment.getProperty(root + Prefix + Donate, Long.class, defaultProfile == null ? null : defaultProfile.getDonate()));
  }

  public long getDonate() {
    return donate;
  }

  public void setDonate(long donate) {
    this.donate = donate;
  }
}
