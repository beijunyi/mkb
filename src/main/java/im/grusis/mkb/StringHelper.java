package im.grusis.mkb;

/**
 * User: Mothership
 * Date: 13-5-19
 * Time: 上午12:14
 */
public class StringHelper {
  public static String convert(String value) {
    try {
      return new String(value.getBytes("GBK"), "UTF-8");
    } catch(Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
