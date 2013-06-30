package im.grusis.mkb.eco.engines;

import im.grusis.mkb.core.emulator.*;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import im.grusis.mkb.core.util.AccountFilter;
import im.grusis.mkb.core.util.MacAddressHelper;
import im.grusis.mkb.core.util.MkbDictionary;
import im.grusis.mkb.eco.util.filter.common.*;
import im.grusis.mkb.eco.util.filter.operators.AndFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Mothership
 * Date: 13-6-29
 * Time: 下午1:13
 */
@Component
public class ProductionEngine {

  private static final Logger LOG = LoggerFactory.getLogger(ProductionEngine.class);

  @Autowired AccountService accountService;
  @Autowired EmulatorUser user;
  @Autowired EmulatorShop shop;
  @Autowired EmulatorLogin login;
  @Autowired EmulatorWeb web;
  @Autowired EmulatorMapStage mapStage;

  public boolean registerNewAccount(String username, String password, int sex, MkbDictionary nicknameDictionary, long serverId, String inviteCode) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    String mac = null;
    while(mac == null) {
      String newMac = MacAddressHelper.getMacAddress();
      if(accountService.findAccountByMac(mac) == null) {
        mac = newMac;
      }
    }
    if(!web.register(username, password, mac, serverId)) {
      return false;
    }
    web.login(username);
    login.passportLogin(username);
    MkbAccount newAccount = accountService.findAccountByUsername(username);
    AccountFilter sameServer = new ServerFilter(newAccount.getServer());
    if(StringUtils.trimToNull(inviteCode) == null) {
      AccountFilter level10 = new NumericPropertyFilter(NumericProperty.Level, CompareOperator.GreaterThanOrEqualTo, 10);
      AccountFilter inviteCount20 = new NumericPropertyFilter(NumericProperty.InviteNumber, CompareOperator.LessThan, 20);
      MkbAccount invitorAccount = accountService.findFirst(new AndFilter(sameServer, level10, inviteCount20));
      if(invitorAccount != null) {
        inviteCode = invitorAccount.getUserInfo().getInviteCode();
      }
    }
    if(StringUtils.trimToNull(inviteCode) == null) {
      MkbAccount invitorAccount = accountService.findFirst(new AndFilter(sameServer, new NumericPropertyFilter(NumericProperty.InviteNumber, CompareOperator.LessThan, 1)));
      if(invitorAccount != null) {
        inviteCode = invitorAccount.getUserInfo().getInviteCode();
      }
    }
    if(StringUtils.trimToNull(inviteCode) == null) {
      MkbAccount invitorAccount = accountService.findFirst(new AndFilter(sameServer, new NumericPropertyFilter(NumericProperty.InviteNumber, CompareOperator.GreaterThanOrEqualTo, 1)));
      if(invitorAccount != null) {
        inviteCode = invitorAccount.getUserInfo().getInviteCode();
      }
    }
    String nickname = null;
    while(nickname == null && nicknameDictionary.hasNext()) {
      String newNickname = nicknameDictionary.next();
      if(user.editNickName(username, sex, inviteCode, newNickname)) {
        nickname = newNickname;
      }
    }
    if(nickname == null) {
      LOG.error("Nickname dictionary has no more usable instance");
      return false;
    }
    mapStage.editUserMapStages(username, 1);
    mapStage.editUserMapStages(username, 2);
    user.editFresh(username, ItemCode.Tutorial_Fight, ItemCode.Tutorial_Fight_Stages[0]);
    user.editFresh(username, ItemCode.Tutorial_Card, ItemCode.Tutorial_Card_Stages[0]);
    user.editFresh(username, ItemCode.Tutorial_Card, ItemCode.Tutorial_Card_Stages[1]);
    if(StringUtils.trimToNull(inviteCode) != null) {
      shop.buy(username, ItemCode.Ticket);
    }
    return true;
  }

}
