package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:09
 */
public class RuneProfile extends Profile<RuneProfile> {

  public static final String Prefix = "rune.";
  public static final String Update = "update";
  public static final String BuyResource = "buyResource";

  private boolean update;
  private boolean buyResource;

  public RuneProfile(Environment environment, String root, RuneProfile defaultProfile) {
    super(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, RuneProfile defaultProfile) {
    setUpdate(environment.getProperty(root + Prefix + Update, Boolean.class, defaultProfile == null ? null : defaultProfile.isUpdate()));
    setBuyResource(environment.getProperty(root + Prefix + BuyResource, Boolean.class, defaultProfile == null ? null : defaultProfile.isBuyResource()));
  }

  public boolean isUpdate() {
    return update;
  }

  public void setUpdate(boolean update) {
    this.update = update;
  }

  public boolean isBuyResource() {
    return buyResource;
  }

  public void setBuyResource(boolean buyResource) {
    this.buyResource = buyResource;
  }
}
