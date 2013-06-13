package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:09
 */
public class CardProfile extends Profile<CardProfile> {

  public static final String Prefix = "card.";
  public static final String Upgrade = "upgrade";
  public static final String BuyResource = "buyResource";

  private boolean upgrade;
  private boolean buyResource;

  public CardProfile(Environment environment, String root, CardProfile defaultProfile) {
    super(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, CardProfile defaultProfile) {
    setUpgrade(environment.getProperty(root + Prefix + Upgrade, Boolean.class, defaultProfile == null ? null : defaultProfile.isUpgrade()));
    setBuyResource(environment.getProperty(root + Prefix + BuyResource, Boolean.class, defaultProfile == null ? null : defaultProfile.isBuyResource()));
  }

  public boolean isUpgrade() {
    return upgrade;
  }

  public void setUpgrade(boolean upgrade) {
    this.upgrade = upgrade;
  }

  public boolean isBuyResource() {
    return buyResource;
  }

  public void setBuyResource(boolean buyResource) {
    this.buyResource = buyResource;
  }
}
