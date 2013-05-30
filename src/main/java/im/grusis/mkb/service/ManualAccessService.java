package im.grusis.mkb.service;

import java.util.HashMap;
import java.util.Map;

import im.grusis.mkb.emulator.core.MkbCore;
import im.grusis.mkb.emulator.core.model.basic.PassportLogin;
import im.grusis.mkb.emulator.core.model.basic.UserInfo;
import im.grusis.mkb.emulator.core.model.response.GameDataFactory;
import im.grusis.mkb.emulator.core.model.response.UserInfoResponse;
import im.grusis.mkb.emulator.passport.PassportHelper;
import im.grusis.mkb.emulator.passport.model.basic.LoginInformation;
import im.grusis.mkb.emulator.passport.model.request.LoginRequest;
import im.grusis.mkb.emulator.passport.model.response.LoginInformationResponse;
import im.grusis.mkb.util.MacAddressHelper;
import im.grusis.mkb.internal.MkAccount;
import im.grusis.mkb.repository.AccountRepository;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Mothership
 * Date: 13-5-26
 * Time: 下午8:19
 */
@Service
public class ManualAccessService extends MkbService {

  @Autowired
  private AccountRepository accountRepository;

  private Map<String, MkbCore> coreMap = new HashMap<String, MkbCore>();

  public String doAction(String username, String service, String action, Map<String, String> params) {
    MkbCore core = coreMap.get(username);
    return core.doAction(service, action, params);
  }

  public PassportLogin login(String username, String password) {
    DefaultHttpClient httpClient = new DefaultHttpClient();
    MkAccount account = accountRepository.getAccount(username);
    if(account == null) {
      account = new MkAccount();
      account.setUsername(username);
      account.setPassword(password);
      account.setMac(MacAddressHelper.getMacAddress());
    }
    String mac = account.getMac();
    LoginInformationResponse response = PassportHelper.request(new LoginRequest(username, password, mac), LoginInformationResponse.class, httpClient);
    LoginInformation li = response.getReturnObjs();
    MkbCore core = new MkbCore(li.getGS_IP(), li.getUserName(), li.getU_ID(), li.getKey(), mac, li.getTimestamp(), httpClient);
    coreMap.put(username, core);
    return core.doPassportLogin();
  }

  public UserInfo getUserInfo(String username) {
    String responseString = doAction(username, "user.php", "GetUserInfo", null);
    return GameDataFactory.getGameData(responseString, UserInfoResponse.class).getData();
  }

}
