package im.grusis.mkb.eco.model;

public class MapPoolSettings extends UserPoolSettings<MapSettings> {
  public MapPoolSettings(String name) {
    super(name, new MapSettings(72));
  }
}
