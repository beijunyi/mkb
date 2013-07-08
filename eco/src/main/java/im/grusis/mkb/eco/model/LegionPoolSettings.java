package im.grusis.mkb.eco.model;

public class LegionPoolSettings extends UserPoolSettings<LegionSettings> {
  public LegionPoolSettings(String name) {
    super(name, new LegionSettings());
  }
}
