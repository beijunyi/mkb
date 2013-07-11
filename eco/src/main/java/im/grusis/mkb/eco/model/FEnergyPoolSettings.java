package im.grusis.mkb.eco.model;

public class FenergyPoolSettings extends UserPoolSettings<FenergySettings> {
  public FenergyPoolSettings(String name) {
    super(name, new FenergySettings());
  }
}
