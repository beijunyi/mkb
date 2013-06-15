package im.grusis.mkb.eco.configs;

/**
 * User: Mothership
 * Date: 13-6-15
 * Time: 下午8:16
 */
public class CardConfig {

  public static final String CardConfigPrefix = "card.";
  public static final String UpgradeMaxNumberSuffix = ".upgrade";
  public static final String KeepMaxNumberSuffix = ".keep";

  private int id;
  private int upgrade;
  private int keep;

  public CardConfig() {
  }

  public CardConfig(int id, int upgrade, int keep) {
    this.id = id;
    this.upgrade = upgrade;
    this.keep = keep;
  }

  public int getId() {
    return id;
  }

  public int getUpgrade() {
    return upgrade;
  }

  public int getKeep() {
    return keep;
  }

}
