package im.grusis.mkb.eco.model;

public class BossSettings {
  private int specialCardGroup;

  public BossSettings() {
  }

  public static BossSettings DefaultBossSettings() {
    BossSettings ret = new BossSettings();
    ret.setSpecialCardGroup(1);
    return ret;
  }

  public int getSpecialCardGroup() {
    return specialCardGroup;
  }

  public void setSpecialCardGroup(int specialCardGroup) {
    this.specialCardGroup = specialCardGroup;
  }
}
