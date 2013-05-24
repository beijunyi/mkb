package im.grusis.mkb.connection;

import im.grusis.mkb.connection.model.response.ServerInformation;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: Mothership
 * Date: 13-5-23
 * Time: 下午8:25
 */
public class registerhelperTest {

  @Test
  public void overallTest() {
    ServerInformation si = RegisterHelper.getServerInformation();
    Assert.assertFalse(si.getReturnObjs().getGAME_SERVER().isEmpty());
  }
}
