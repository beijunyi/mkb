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

  @Test
  public void idEncryptTest() {
    String value = XXTEA.intArrayToString(new int[] {134634578, 67781405, 303436127, 84559449, 71762523, 34361867, 184569862, 1342529024, 67589123, 100728918, 22481747, 84346707, 39652180, 413962, 51731207, 1593990215, 152502545, 201525781, 56037727, 84214359, 257229139, 101012742, 201741568, 1409700870, 33775361, 67111254, 72812372, 460369, 1163795029, 302993417, 1259094093, 1594512654, 83907601, 591440, 123077715, 84149847, 38994007, 33837569, 135157506, 1560302592, 16864004, 117899860, 1771591, 171}, true);
    String result = Client.idEncrypt(value);
    Assert.assertEquals(result, "a:3:{s:1:\"p\";s:39:\"283922273486502260013681112736384903039\";s:1:\"g\";s:39:\"135753190954064714225129600901141433767\";s:1:\"y\";s:39:\"257669567075343586423126590423875432272\";}");
  }
}
