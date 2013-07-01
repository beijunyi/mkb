package im.grusis.mkb.eco.model;

import java.util.TreeSet;

public class UserPoolSettings extends EcoSettings {

  TreeSet<String> usernames;

  public UserPoolSettings(String name) {
    super(name);
  }

  public void addUser(String username) {
    if(usernames == null) {
      usernames = new TreeSet<String>();
    }
    usernames.add(username);
  }

  public TreeSet<String> getUsernames() {
    return usernames;
  }
}
