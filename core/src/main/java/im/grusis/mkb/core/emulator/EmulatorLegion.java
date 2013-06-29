package im.grusis.mkb.core.emulator;

import im.grusis.mkb.core.emulator.game.model.basic.*;
import im.grusis.mkb.core.emulator.game.model.response.*;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Mothership
 * Date: 13-6-28
 * Time: 下午11:28
 */
@Component
public class EmulatorLegion {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorLegion.class);

  @Autowired AccountService accountService;
  @Autowired EmulatorCore core;
  @Autowired EmulatorUser user;

  public Tech getTech(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    LegionGetTechResponse response = core.gameDoAction(username, "legion.php", "GetTech", null, LegionGetTechResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public UserLegion getUserLegion(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.getUserInfo(username, false);
    LOG.debug("{} is retrieving user legion information", userInfo);
    MkbAccount account = accountService.findAccountByUsername(username);
    UserLegion userLegion;
    if(refresh || (userLegion = account.getUserLegion()) == null) {
      LegionGetUserLegionResponse response = core.gameDoAction(username, "legion.php", "GetUserLegion", null, LegionGetUserLegionResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      userLegion = response.getData();
      account.setUserLegion(userLegion);
      accountService.saveAccount(account);
    }
    LOG.debug("{} has successfully retrieved user legion information", userInfo);
    return userLegion;
  }

  public Legions getLegions(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.getUserInfo(username, false);
    LOG.debug("{} is retrieving legions information", userInfo);
    LegionGetLegionsResponse response = core.gameDoAction(username, "legion.php", "GetLegions", null, LegionGetLegionsResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    Legions legions = response.getData();
    LOG.debug("{} has successfully retrieved legions information", userInfo);
    return legions;
  }

  public Members getMember(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    LegionGetMemberResponse response = core.gameDoAction(username, "legion.php", "GetMember", null, LegionGetMemberResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

}
