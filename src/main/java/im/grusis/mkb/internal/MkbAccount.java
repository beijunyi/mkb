package im.grusis.mkb.internal;

import im.grusis.mkb.emulator.emulator.core.model.basic.CardGroup;
import im.grusis.mkb.emulator.emulator.core.model.basic.UserInfo;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 下午10:04
 */
public class MkbAccount {
  private String username;
  private String password;
  private String mac;
  private String inviteCode;
  private int inviteCount = 0;

  private UserInfo userInfo;
  private CardGroup cardGroup;


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getInviteCode() {
    return inviteCode;
  }

  public void setInviteCode(String inviteCode) {
    this.inviteCode = inviteCode;
  }

  public int getInviteCount() {
    return inviteCount;
  }

  public void setInviteCount(int inviteCount) {
    this.inviteCount = inviteCount;
  }

  public UserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }

  public CardGroup getCardGroup() {
    return cardGroup;
  }

  public void setCardGroup(CardGroup cardGroup) {
    this.cardGroup = cardGroup;
  }
}
