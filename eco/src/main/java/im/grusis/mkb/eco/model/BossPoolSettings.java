package im.grusis.mkb.eco.model;

public class BossPoolSettings extends UserPoolSettings<BossSettings> {
  public BossPoolSettings(String name) {
    super(name, BossSettings.DefaultBossSettings());
  }
}
