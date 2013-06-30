package im.grusis.mkb.core.emulator;

import java.util.LinkedHashMap;
import java.util.Map;

import im.grusis.mkb.core.emulator.game.model.basic.UserInfo;
import im.grusis.mkb.core.emulator.game.model.response.*;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import im.grusis.mkb.core.service.ArchiveService;
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
public class EmulatorUser {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorUser.class);

  @Autowired AccountService accountService;
  @Autowired ArchiveService archiveService;
  @Autowired EmulatorCore core;

  public boolean editNickName(String username, int sex, String inviteCode, String nickname) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    LoginToken token = core.getLoginToken(username);
    if(archiveService.existNickname(token.getServerName(), nickname)) {
      LOG.debug("Cannot set nickname for account {}. {} is already in use", username, nickname);
      return false;
    }
    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("Sex", Integer.toString(sex));
    paramMap.put("InviteCode", inviteCode);
    paramMap.put("NickName", nickname);
    UserEditNickNameResponse response = core.gameDoAction(username, "user.php", "EditNickName", paramMap, UserEditNickNameResponse.class);
    if(response.badRequest()) {
      if(response.duplicateNickName()) {
        LOG.debug("Cannot set nickname for account {}. Desired nickname {} is already in use", username, nickname);
        archiveService.addNickname(token.getServerName(), nickname);
      } else if(response.tooLong()) {
        LOG.error("Cannot set nickname for account {}. Desired nickname {} has too many characters", username, nickname);
      } else {
        throw new UnknownErrorException();
      }
      return false;
    }
    archiveService.addNickname(token.getServerName(), nickname);
    return true;
  }

  public boolean editFresh(String username, int type, int stage) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("FreshStep", type + "_" + stage);
    UserEditFreshResponse response = core.gameDoAction(username, "user.php", "EditFresh", paramMap, UserEditFreshResponse.class);
    if(response.badRequest()) {
      if(response.alreadyFinished()) {
        LOG.error("Cannot skip tutorial. {} has already finished tutorial stage {}", username, stage);
        return false;
      } else {
        throw new UnknownErrorException();
      }
    }
    return true;
  }

  public boolean getUserSalary(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserGetUserSalaryResponse response = core.gameDoAction(username, "user.php", "GetUserSalary", null, UserGetUserSalaryResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public boolean awardSalary(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserAwardSalaryResponse response = core.gameDoAction(username, "user.php", "AwardSalary", null, UserAwardSalaryResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public UserInfo getUserInfo(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    UserInfo userInfo;
    if(refresh || (userInfo = account.getUserInfo()) == null) {
      UserGetUserInfoResponse response = core.gameDoAction(username, "user.php", "GetUserinfo", null, UserGetUserInfoResponse.class);
      if(response.badRequest()) {
        if(response.isRequireNickName()) {
          return null;
        }
        throw new UnknownErrorException();
      }
      userInfo = response.getData();
      account.setUserInfo(userInfo);
      accountService.saveAccount(account);
      archiveService.addNickname(core.getLoginToken(username).getServerName(), userInfo.getNickName());
    }
    return userInfo;
  }

}
