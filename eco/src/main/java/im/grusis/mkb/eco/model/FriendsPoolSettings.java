package im.grusis.mkb.eco.model;

public class FriendsPoolSettings extends UserPoolSettings<FriendsSettings> {
  public FriendsPoolSettings(String name) {
    super(name, new FriendsSettings());
  }
}
