package im.grusis.mkb.web.service;

import java.util.HashMap;
import java.util.Map;

import im.grusis.mkb.connection.passport.PassportHelper;
import im.grusis.mkb.connection.passport.model.basic.LoginInformation;
import im.grusis.mkb.connection.passport.model.request.LoginRequest;
import im.grusis.mkb.connection.passport.model.response.LoginInformationResponse;
import im.grusis.mkb.util.MacAddressHelper;
import org.springframework.stereotype.Service;

/**
 * User: Mothership
 * Date: 13-5-26
 * Time: 下午8:19
 */
@Service
public class ManualAccessService extends MkbService {

  private Map<String, String> macMap = new HashMap<String, String>();

  private String getMac(String username) {
    String mac = macMap.get(username);
    if(mac == null) {
      mac = MacAddressHelper.getMacAddress();
      macMap.put(username, mac);
    }
    return mac;
  }

  public LoginInformation login(String username, String password) {
    String mac = getMac(username);
    LoginInformationResponse r = PassportHelper.request(new LoginRequest(username, password, mac), LoginInformationResponse.class);
    return r.getReturnObjs();
  }

}
