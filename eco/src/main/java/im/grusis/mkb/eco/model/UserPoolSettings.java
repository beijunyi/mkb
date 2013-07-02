package im.grusis.mkb.eco.model;

import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class UserPoolSettings extends EcoSettings {

  TreeSet<String> usernames;

  public UserPoolSettings(String name) {
    super(name);
  }

  public void addUsers(List<String> users) {
    if(usernames == null) {
      usernames = new TreeSet<String>();
    }
    usernames.addAll(users);
  }

  public void removeUsers(List<String> users) {
    if(usernames != null) {
      usernames.removeAll(users);
    }
  }

  public TreeSet<String> getUsernames() {
    return usernames;
  }
}
