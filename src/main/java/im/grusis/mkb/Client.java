package im.grusis.mkb;

import java.math.BigInteger;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

/**
 * User: Mothership
 * Date: 13-5-16
 * Time: 下午11:43
 */
public class Client {

  public String key;

  public String encryptArgs(String data, int t, int n, int r) {
    if(t >= n) {
      data = XXTEA.encrypt(data, key);
    }
    if(t >= r) {
      data = Encoder.btoa(data);
    }
    return data;
  }

  public String idEncrypt(String data) {
    String id = "3f52f005e3f0b506b8e63a504b784f3e";
    int idLength = id.length();
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < data.length(); i++) {
      int o = i % idLength;
      sb.append(XXTEA.fromCharCode(data.charAt(i) ^ id.charAt(o)));
    }
    return sb.toString();
  }

  public String generateKey(String data) {
    return generateKey(data, new BigInteger(127, new Random()));
  }

  public String generateKey(String data, BigInteger s) {
    data = idEncrypt(Encoder.atob(data));
    Map dataMap = Unserializer.unserializeMap(data);
    BigInteger n = new BigInteger((String)dataMap.get("p"));
    BigInteger r = new BigInteger((String)dataMap.get("g"));
    BigInteger i = new BigInteger((String)dataMap.get("y"));
    BigInteger o = i.modPow(s, n);
      String oString = BigIntegerHelper.BigIntegerToString(o);
      int f = 16 - oString.length();
      String[] l = new String[f + 1];
      for(int h = 0; h < f; h++) {
        l[h] = "\0";
      }
      l[f] = oString;
      key = StringUtils.join(l);
    String p = r.modPow(s, n).toString();
    p = idEncrypt(p);
    return Encoder.btoa(p);
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
