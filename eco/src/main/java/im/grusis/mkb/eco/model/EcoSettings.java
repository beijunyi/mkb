package im.grusis.mkb.eco.model;

public abstract class EcoSettings {
  protected String name;

  public EcoSettings(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
