package im.grusis.mkb.eco.model;

public class BossSettings {
  private int specialCardGroup;

  public BossSettings() {
  }

  public BossSettings(int specialCardGroup) {
    this.specialCardGroup = specialCardGroup;
  }

  public int getSpecialCardGroup() {
    return specialCardGroup;
  }

  public void setSpecialCardGroup(int specialCardGroup) {
    this.specialCardGroup = specialCardGroup;
  }
}
