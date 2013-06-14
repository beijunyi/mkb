package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:09
 */
public class RuneProfile extends Profile<RuneProfile> {

  public static final String Rune = "rune.";
  public static final String Update = "update";
  public static final boolean UpdateDefault = true;
  public static final String BuyResource = "buyResource";
  public static final boolean BuyResourceDefault = true;

  private boolean update;
  private boolean buyResource;

  public RuneProfile(Environment environment, String root, RuneProfile defaultProfile) {
    super(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, RuneProfile defaultProfile) {
    setUpdate(environment.getProperty(root + Rune + Update, Boolean.class, defaultProfile == null ? UpdateDefault : defaultProfile.isUpdate()));
    setBuyResource(environment.getProperty(root + Rune + BuyResource, Boolean.class, defaultProfile == null ? BuyResourceDefault : defaultProfile.isBuyResource()));
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
