package im.grusis.mkb;

import java.math.BigInteger;

import org.testng.Assert;
import org.testng.annotations.Test;
import im.grusis.mkb.core.util.Client;
import im.grusis.mkb.core.util.XXTEA;

/**
 * User: Mothership
 * Date: 13-5-17
 * Time: 上午12:23
 */
public class clientTest {

  @Test
  public void encryptArgsTest() {
    String value = "a:1:{i:0;s:57:\"{\"gameName\":\"CARD-ANDROID-CHS\",\"locale\":\"\",\"udid\":\"null\"}\";}";
    Client client = new Client(null, XXTEA.intArrayToString(new int[]{-548756353, 8497807, 604538387, 48853545, 16}, true));
    int t = 2;
    int n = 1;
    int r = 0;
    Assert.assertEquals(client.encryptArgs(value, t, n, r), "nmt2heA7snjr/2WmXsJ8PoeNRKHPdcUrBMHHQLpvwtY77Kct6Bu6btvz0+F4rCce8irW9Aa27O48no8FKI4kzZMVuK+FVahiqRHLCJJGpGk=");
  }

  @Test
  public void idEncryptTest() {
    String value = XXTEA.intArrayToString(new int[]{134634578, 67781405, 303436127, 84559449, 71762523, 34361867, 184569862, 1342529024, 67589123, 100728918, 22481747, 84346707, 39652180, 413962, 51731207, 1593990215, 152502545, 201525781, 56037727, 84214359, 257229139, 101012742, 201741568, 1409700870, 33775361, 67111254, 72812372, 460369, 1163795029, 302993417, 1259094093, 1594512654, 83907601, 591440, 123077715, 84149847, 38994007, 33837569, 135157506, 1560302592, 16864004, 117899860, 1771591, 171}, true);
    Client client = new Client();
    String result = client.idEncrypt(value);
    Assert.assertEquals(result, "a:3:{s:1:\"p\";s:39:\"283922273486502260013681112736384903039\";s:1:\"g\";s:39:\"135753190954064714225129600901141433767\";s:1:\"y\";s:39:\"257669567075343586423126590423875432272\";}");
  }

  @Test
  public void generateKeyTest() {
    Client client = new Client();
    String str;
    BigInteger random;
    String result;
    String key;
    str = "UlwGCB1DCgRfERYSWUYKBVsCRwQAWAUFBVQBAQRQClIBVAwAUQUDBFMHVAhUDAcBWgBWDgZVBwMAURUDR1wCXxEBFwkVCgMMXxFXBVsDAgZQDFMEA1gABQxbBggCUABVAV4DClEJBwVRBV8CVgQFDlcaXkUJUA8STUAMSw5VCl8RVwwDUQcBA10DUgdWDQYBUg1VAQdSBwcHVQYBA1IEUQtTBQBVBgIARwgb";
    random = new BigInteger("98255119643918749234664962155133600351");
    result = client.generateKey(str, random);
    key = client.getKey();
    Assert.assertEquals(result, XXTEA.intArrayToString(new int[]{1314483265, 1245196610, 1363310402, 1180003393, 1115115862, 1245801537, 1145665366, 1182224726, 1198731585, 1480737091, 1160801345, 1097037653, 1246839126, 52}, true));
    Assert.assertEquals(key, XXTEA.intArrayToString(new int[]{1962971704, -369762588, -1142715736, -1043371245, 16}, true));

    str = "UlwGCB1DCgRfERYSWUYKBVsCRwQKUwMIBlAHDgFRAVcAVgABUgQDAFYHUgVQBQAFVgFVAAVSBgUNWxUDR1wCXxEBFwkVCgMMXxFXBFANBwJTCFAHBVQNBwxTAQsEUAZXC1IHA18GAQdWAFUAUgwFA1MaXkUJUA8STUAMSw5VC18RVQMKUgkBBFYHXgZQDAgPVgFVAQdYAwYNUA4KB1cLVwNQBQFQAwIXXk4=";
    result = client.generateKey(str, random);
    Assert.assertEquals(result, "AVECAlEIAAFXClMFVwUGBlsAVgQHVAMGBlEOCQdRAlQBVgIKUQUJ");

    str = "UlwGCB1DCgRfERYSWUYKBVsCRwcLWQMJBVsODgVeAVcFVgYHXwEAAFUFVgNRDQkDWw9QAQBUAQEAVRUDR1wCXxEBFwkVCgMMXxFXBlAGCANSCVUDClYBCQVXBQ0CVARWC18DAFICAwVXAlEAUgYCBVEaXkUJUA8STUAMSw5VC18RVwYCUwMAB1UCVAlWAQAPVA9WBAdRAQIGWw4KAVMGVgpVBQZRCAUXXk4=";
    random = new BigInteger("87478531475386817044313038626308168951");
    result = client.generateKey(str, random);
    key = client.getKey();
    Assert.assertEquals(result, "C1UGBVYFCAJcBVYCWgYED1QOVQEFVgYGAloECwZWC1QFXwMKVgQ=");
    Assert.assertEquals(key, XXTEA.intArrayToString(new int[]{979284272, -241882447, -1463597399, 158315569, 16}, true));

  }
}
