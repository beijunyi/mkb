package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:09
 */
public class CardProfile extends Profile<CardProfile> {

  public static final String Card = "card.";
  public static final String Upgrade = "upgrade";
  public static final boolean UpgradeDefault = true;
  public static final String BuyResource = "buyResource";
  public static final boolean  BuyResourceDefault = true;

  private boolean upgrade;
  private boolean buyResource;

  public CardProfile(Environment environment, String root, CardProfile defaultProfile) {
    super(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, CardProfile defaultProfile) {
    setUpgrade(environment.getProperty(root + Card + Upgrade, Boolean.class, defaultProfile == null ? UpgradeDefault : defaultProfile.isUpgrade()));
    setBuyResource(environment.getProperty(root + Card + BuyResource, Boolean.class, defaultProfile == null ? BuyResourceDefault : defaultProfile.isBuyResource()));
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
