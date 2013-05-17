package im.grusis.mkb;

/**
 * User: Mothership
 * Date: 13-5-16
 * Time: 下午11:43
 */
public class Client {
  public static String encryptArgs(String data, String key, int t, int n, int r) {
    if(t >= n) {
      data = XXTEA.encrypt(data, key);
    }
    if(t >= r) {
      data = Encoder.btoa(data);
    }
    return data;
  }
}
