package im.grusis.mkb;

/**
 * User: Mothership
 * Date: 13-5-13
 * Time: 下午10:49
 */
public class MkbUtils {
  public static boolean isInteger(Object o) {
    return o instanceof Integer || isInteger(o.toString(), 10);
  }

  public static boolean isInteger(String s, int radix) {
    if(s.isEmpty()) return false;
    for(int i = 0; i < s.length(); i++) {
      if(i == 0 && s.charAt(i) == '-') {
        if(s.length() == 1) return false;
        else continue;
      }
      if(Character.digit(s.charAt(i), radix) < 0) return false;
    }
    return true;
  }
}
