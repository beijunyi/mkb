package im.grusis.mkb.eco.model;

public class MazePoolSettings extends UserPoolSettings<MazeSettings> {
  public MazePoolSettings(String name) {
    super(name, new MazeSettings(8));
  }
}
