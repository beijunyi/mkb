package im.grusis.mkb;

import im.grusis.mkb.connection.core.MkbCore;
import im.grusis.mkb.connection.passport.PassportHelper;
import im.grusis.mkb.connection.passport.model.basic.LoginInformation;
import im.grusis.mkb.connection.passport.model.request.LoginRequest;
import im.grusis.mkb.connection.passport.model.response.LoginInformationResponse;
import im.grusis.mkb.util.MacAddressHelper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.testng.annotations.Test;

/**
 * User: Mothership
 * Date: 13-5-18
 * Time: 下午7:43
 */
public class TEMP {
  @Test
  public void T() {
    DefaultHttpClient httpClient = new DefaultHttpClient();
    LoginInformation li = PassportHelper.request(new LoginRequest("mewrobot0001", "123456", null), LoginInformationResponse.class, httpClient).getReturnObjs();
    MkbCore core = new MkbCore(li.getGS_IP(), li.getUserName(), li.getU_ID(), li.getKey(), MacAddressHelper.getMacAddress(), li.getTimestamp(), httpClient);
  }
}
