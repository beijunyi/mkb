package im.grusis.mkb.eco.model;

public class FEnergyPoolSettings extends UserPoolSettings<FenergySettings> {
  public FEnergyPoolSettings(String name) {
    super(name, new FenergySettings());
  }
}
