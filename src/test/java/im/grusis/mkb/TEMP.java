package im.grusis.mkb;

import im.grusis.mkb.connection.PassportHelper;
import im.grusis.mkb.connection.model.request.LoginRequest;
import im.grusis.mkb.connection.model.response.LoginInformationResponse;
import org.testng.annotations.Test;

/**
 * User: Mothership
 * Date: 13-5-18
 * Time: 下午7:43
 */
public class TEMP {
  @Test
  public void T() {
    LoginInformationResponse ir = PassportHelper.request(new LoginRequest("mewrobot0001", "123456"), LoginInformationResponse.class);
    System.currentTimeMillis();
  }
}
