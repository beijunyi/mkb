package im.grusis.mkb.core.emulator;

import java.util.LinkedHashMap;
import java.util.Map;

import im.grusis.mkb.core.emulator.game.model.basic.PassportLogin;
import im.grusis.mkb.core.emulator.game.model.response.LoginPassportLoginResponse;
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
public class EmulatorLogin {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorLogin.class);

  @Autowired AccountService accountService;
  @Autowired EmulatorCore core;
  @Autowired EmulatorWeb web;
  @Autowired EmulatorUser user;

  public PassportLogin passportLogin(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    LOG.debug("Starting passport login for account {}", username);
    LoginToken token = core.getLoginToken(username);
    if(token == null) {
      LOG.debug("Cannot do passport login. {} does not have a login token", username);
      MkbAccount account = accountService.findAccountByUsername(username);
      token = account.getProfile();
      if(token != null) {
        LOG.debug("Login token for account {} is found. Now continue passport login", username);
        core.setLoginToken(username, token);
      } else {
        LOG.debug("Login token for account {} is not found. Now try web login", username);
        token = web.login(username);
      }
    }
    LOG.debug("Obtained login token for account {} as login token", username);
    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("time", Long.toString(token.getTime()));
    paramMap.put("key", token.getKey());
    paramMap.put("Udid", token.getMac());
    paramMap.put("Password", Long.toString(token.getUid()));
    paramMap.put("Devicetoken", "");
    paramMap.put("UserName", username.toLowerCase());
    paramMap.put("Origin", "com");
    LoginPassportLoginResponse response = core.gameDoAction(username, "login.php", "PassportLogin", paramMap, LoginPassportLoginResponse.class);
    if(response.badRequest()) {
      LOG.info("Passport login for account {} has failed. Now try web login", username);
      web.login(username);
      return passportLogin(username);
    }
    user.getUserInfo(username, true);
    return response.getData();
  }

}
