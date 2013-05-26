package im.grusis.mkb;

import java.math.BigInteger;

import org.testng.Assert;
import org.testng.annotations.Test;
import im.grusis.mkb.util.BigIntegerHelper;
import im.grusis.mkb.util.XXTEA;

/**
 * User: Mothership
 * Date: 13-5-18
 * Time: 下午11:38
 */
public class bigintergerhelperTest {

  @Test
  public void bigIntegerToStringTest() {
    String string = "147460619727137284178244279398849109479";
    Assert.assertEquals(BigIntegerHelper.BigIntegerToString(new BigInteger(string)), XXTEA.intArrayToString(new int[]{-538841234, -763429275, -239813028, -404922168, 16}, true));
  }
}
