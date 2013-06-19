package im.grusis.mkb.core.service;

import java.util.HashMap;
import java.util.Map;

import im.grusis.mkb.core.emulator.MkbEmulator;
import im.grusis.mkb.core.emulator.game.MkbGame;
import im.grusis.mkb.core.emulator.game.model.basic.PassportLogin;
import im.grusis.mkb.core.emulator.game.model.basic.UserInfo;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.repository.AccountRepository;
import im.grusis.mkb.core.util.MacAddressHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Mothership
 * Date: 13-5-26
 * Time: 下午8:19
 */
@Service
public class ManualAccessService extends MkbService {

  @Autowired private AccountRepository accountRepository;
  @Autowired private MkbEmulator mkbEmulator;

  private Map<String, MkbGame> coreMap = new HashMap<String, MkbGame>();

  public String doAction(String username, String service, String action, Map<String, String> params) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    return mkbEmulator.gameDoAction(username, service, action, params);
  }

  public PassportLogin login(String username, String password) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    mkbEmulator.webLogin(username, password, MacAddressHelper.getMacAddress());
    return mkbEmulator.gamePassportLogin(username);
//    MkbAccount account = accountRepository.getAccount(username);
//    if(account == null) {
//      account = new MkbAccount();
//      account.setUsername(username);
//      account.setPassword(password);
//      account.setMac(MacAddressHelper.getMacAddress());
//    }
//    String mac = account.getMac();
//    LoginInformationResponse response = mkbEmulator.passportRequest(new LoginRequest(username, password, mac), LoginInformationResponse.class);
//    LoginInformation li = response.getReturnObjs();
//    MkbGame core = mkbEmulator.getMkbCore(li.getGS_IP(), li.getUserName(), li.getU_ID(), li.getKey(), mac, li.getTimestamp());
//    coreMap.put(username, core);
//    try {
//      return core.doPassportLogin();
//    } catch(Exception e) {
//      e.printStackTrace();
//    }
//          return null;
  }

  public UserInfo getUserInfo(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException{
    return mkbEmulator.gameGetUserInfo(username, true);
//    String responseString = doAction(username, "user.php", "GetUserInfo", null);
//    return GameDataFactory.getGameData(responseString, UserGetUserInfoResponse.class).getData();
  }

}
