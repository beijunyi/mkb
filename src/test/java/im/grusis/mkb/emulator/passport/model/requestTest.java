package im.grusis.mkb.emulator.passport.model;

import im.grusis.mkb.emulator.passport.model.request.LoginRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: Mothership
 * Date: 13-5-25
 * Time: 下午3:11
 */
public class requestTest {
  @Test
  public void loginRequestTest() {
    LoginRequest lr = new LoginRequest("mewrobot0001", "123456", null);
    Assert.assertEquals(lr.getFunc(), "login");
    Assert.assertEquals(lr.getArgs(), "a:1:{i:0;s:148:\"{\"userName\":\"mewrobot0001\",\"userPassword\":\"123456\",\"gameName\":\"CARD-ANDROID-CHS\",\"udid\":\"null\",\"clientType\":\"flash\",\"releaseChannel\":\"\",\"locale\":\"\"}\";}");
  }
}
