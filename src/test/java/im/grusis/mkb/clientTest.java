package im.grusis.mkb;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: Mothership
 * Date: 13-5-17
 * Time: 上午12:23
 */
public class clientTest {

  @Test
  public void encryptArgsTest() {
    String value = "a:1:{i:0;s:57:\"{\"gameName\":\"CARD-ANDROID-CHS\",\"locale\":\"\",\"udid\":\"null\"}\";}";
    String key = XXTEA.intArrayToString(new int[] {-548756353, 8497807, 604538387, 48853545, 16}, true);
    int t = 2;
    int n = 1;
    int r = 0;
    Assert.assertEquals(Client.encryptArgs(value, key, t, n, r), "nmt2heA7snjr/2WmXsJ8PoeNRKHPdcUrBMHHQLpvwtY77Kct6Bu6btvz0+F4rCce8irW9Aa27O48no8FKI4kzZMVuK+FVahiqRHLCJJGpGk=");
  }
}
