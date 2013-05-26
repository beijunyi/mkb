package im.grusis.mkb;

import im.grusis.mkb.connection.PassportHelper;
import im.grusis.mkb.connection.model.request.ServerRequest;
import im.grusis.mkb.connection.model.response.ServerInformationResponse;
import org.testng.annotations.Test;

/**
 * User: Mothership
 * Date: 13-5-18
 * Time: 下午7:43
 */
public class TEMP {
  @Test
  public void T() {
    ServerInformationResponse ir = PassportHelper.request(new ServerRequest(), ServerInformationResponse.class);
    System.currentTimeMillis();
  }
}
