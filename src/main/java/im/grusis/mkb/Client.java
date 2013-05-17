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

  public static String idEncrypt(String data) {
    String id = "3f52f005e3f0b506b8e63a504b784f3e";
    int idLength = id.length();
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < data.length(); i++) {
      int o = i % idLength;
      sb.append(XXTEA.fromCharCode(data.charAt(i) ^ id.charAt(o)));
    }
    return sb.toString();
  }
}
