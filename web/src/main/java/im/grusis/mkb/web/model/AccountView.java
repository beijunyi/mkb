package im.grusis.mkb.web.model;

/**
 * User: Mothership
 * Date: 13-6-25
 * Time: 下午5:53
 */
public class AccountView {
  private String server;
  private String username;
  private String nickname;
  private int level;

  public AccountView(String server, String username, String nickname, int level) {
    this.server = server;
    this.username = username;
    this.nickname = nickname;
    this.level = level;
  }

  public String getServer() {
    return server;
  }

  public String getUsername() {
    return username;
  }

  public String getNickname() {
    return nickname;
  }

  public int getLevel() {
    return level;
  }
}
