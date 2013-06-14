package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:09
 */
public class LegionProfile extends Profile<LegionProfile> {

  public static final String Legion = "legion.";
  public static final String Donate = "donate";
  public static final long DonateDefault = 30000;

  private long donate;

  public LegionProfile(Environment environment, String root, LegionProfile defaultProfile) {
    super(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, LegionProfile defaultProfile) {
    setDonate(environment.getProperty(root + Legion + Donate, Long.class, defaultProfile == null ? DonateDefault : defaultProfile.getDonate()));
  }

  public long getDonate() {
    return donate;
  }

  public void setDonate(long donate) {
    this.donate = donate;
  }
}
