package im.grusis.mkb.web.model;

import java.util.List;

public class UserPool {
  private List<String> usernames;

  public List<String> getUsernames() {
    return usernames;
  }

  public void setUsernames(List<String> usernames) {
    this.usernames = usernames;
  }
}
