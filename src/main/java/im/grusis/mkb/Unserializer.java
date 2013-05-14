package im.grusis.mkb;

import java.util.List;

/**
 * User: beij
 * Date: 14/05/13
 * Time: 15:59
 */
public class Unserializer {
  public Object unserializeNull() {
    return null;
  }

  public boolean unserializeBoolean(String value) {
    return value.charAt(0) != '0';
  }

  public int unserializeInteger(String value) {
    return Integer.valueOf(value);
  }

  public double unserializeDouble(String value) {
    if(value.equalsIgnoreCase("NAN")) {
      return Double.NaN;
    }
    if(value.equalsIgnoreCase("INF")) {
      return Double.POSITIVE_INFINITY;
    }
    if(value.equalsIgnoreCase("-INF")) {
      return Double.NEGATIVE_INFINITY;
    }
    return Double.valueOf(value);
  }

  public String unserializeString(String value) {
    int lengthBreak = value.indexOf(":", 1);
    int length = Integer.valueOf(value.substring(1, lengthBreak));
    return value.substring(lengthBreak + 1, lengthBreak + 1 + length);
  }

}
