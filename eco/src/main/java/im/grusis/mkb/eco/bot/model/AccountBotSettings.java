package im.grusis.mkb.eco.bot.model;

/**
 * User: Mothership
 * Date: 13-6-29
 * Time: 下午4:50
 */
public class AccountBotSettings {
  private long serverId;
  private String usernamePrefix;
  private String nicknamePrefix;
  private String password;
  private int gender;
  private int total;

  private String inviteCode;
  private boolean useSameInviteCode;

  public long getServerId() {
    return serverId;
  }

  public void setServerId(long serverId) {
    this.serverId = serverId;
  }

  public String getUsernamePrefix() {
    return usernamePrefix;
  }

  public void setUsernamePrefix(String usernamePrefix) {
    this.usernamePrefix = usernamePrefix;
  }

  public String getNicknamePrefix() {
    return nicknamePrefix;
  }

  public void setNicknamePrefix(String nicknamePrefix) {
    this.nicknamePrefix = nicknamePrefix;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getGender() {
    return gender;
  }

  public void setGender(int gender) {
    this.gender = gender;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public String getInviteCode() {
    return inviteCode;
  }

  public void setInviteCode(String inviteCode) {
    this.inviteCode = inviteCode;
  }

  public boolean isUseSameInviteCode() {
    return useSameInviteCode;
  }

  public void setUseSameInviteCode(boolean useSameInviteCode) {
    this.useSameInviteCode = useSameInviteCode;
  }
}
