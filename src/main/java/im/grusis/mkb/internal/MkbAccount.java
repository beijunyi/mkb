package im.grusis.mkb.internal;

import im.grusis.mkb.emulator.emulator.core.model.basic.*;
import im.grusis.mkb.internal.filters.AccountFilter;

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
  private long userInfoUpdate;
  private UserCards userCards;
  private long userCardsUpdate;
  private CardGroup cardGroup;
  private long cardGroupUpdate;
  private UserMapStages userMapStages;
  private long userMapStagesUpdate;

  public static boolean matches(MkbAccount account, AccountFilter... filters) {
    for(AccountFilter filter : filters) {
      if(!filter.accept(account)) {
        return false;
      }
    }
    return true;
  }


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
    this.userInfoUpdate = System.currentTimeMillis();
  }

  public CardGroup getCardGroup() {
    return cardGroup;
  }

  public void setCardGroup(CardGroup cardGroup) {
    this.cardGroup = cardGroup;
    this.cardGroupUpdate = System.currentTimeMillis();
  }

  public long getUserInfoUpdate() {
    return userInfoUpdate;
  }

  public long getCardGroupUpdate() {
    return cardGroupUpdate;
  }

  public UserMapStages getUserMapStages() {
    return userMapStages;
  }

  public void setUserMapStages(UserMapStages userMapStages) {
    this.userMapStages = userMapStages;
    this.userMapStagesUpdate = System.currentTimeMillis();
  }

  public long getUserMapStagesUpdate() {
    return userMapStagesUpdate;
  }

  public UserCards getUserCards() {
    return userCards;
  }

  public void setUserCards(UserCards userCards) {
    this.userCards = userCards;
    this.userCardsUpdate = System.currentTimeMillis();
  }

  public long getUserCardsUpdate() {
    return userCardsUpdate;
  }
}
