package im.grusis.mkb;

import java.util.*;

/**
 * User: beij
 * Date: 14/05/13
 * Time: 15:59
 */
public class Unserializer {
  public static Object unserializeNull() {
    return null;
  }

  public static boolean unserializeBoolean(String value) {
    return value.charAt(0) != '0';
  }

  public static int unserializeInteger(String value) {
    return Integer.valueOf(value);
  }

  public static double unserializeDouble(String value) {
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

  public static String unserializeString(String value) {
    int lengthBreak = value.indexOf(":", 1);
    int length = Integer.valueOf(value.substring(1, lengthBreak));
    return value.substring(lengthBreak + 2, lengthBreak + 2 + length);
  }

  private static int findEnd(String value, int startIndex) {
    int length = value.length();
    int openBrackets = 0;
    for(int i = startIndex; i < length; i++) {
      if(value.charAt(i) == '{') {
        openBrackets++;
        continue;
      }
      if(value.charAt(i) == '}') {
        openBrackets--;
        if(openBrackets == 0) return i + 1;
        continue;
      }
      if(openBrackets == 0 && value.charAt(i) == ';') return i + 1;
    }
    return -1;
  }

  public static Map<String, Object> unserializeMap(String value) {
    Map<String, Object> result = new LinkedHashMap<String, Object>();
    int sizeBreak = value.indexOf(":", 1);
    int size = Integer.valueOf(value.substring(1, sizeBreak));
    int start = sizeBreak + 2, end;
    String key;
    Object object;
    for(int i = 0; i < size; i++) {
      switch(value.charAt(start++)) {
        case 'i':
          end = value.indexOf(";", start);
          key = Integer.toString(unserializeInteger(value.substring(start, end)));
          break;
        default:
          end = value.indexOf(";", start);
          key = unserializeString(value.substring(start, end));
      }
      start = end + 1;
      end = findEnd(value, start);
      object = unserialize(value.substring(start, end));
      start = end;
      result.put(key, object);
    }
    return result;
  }

  public static Date unserializeDate(String value) {
    Map<String, Object> map = unserializeMap(value);
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, (Integer) map.get("year"));
    calendar.set(Calendar.MONTH, (Integer) map.get("month") - 1);
    calendar.set(Calendar.DAY_OF_MONTH, (Integer) map.get("day"));
    calendar.set(Calendar.HOUR_OF_DAY, (Integer) map.get("hour"));
    calendar.set(Calendar.MINUTE, (Integer) map.get("minute"));
    calendar.set(Calendar.SECOND, (Integer) map.get("second"));
    calendar.set(Calendar.MILLISECOND, (Integer) map.get("millisecond"));
    return calendar.getTime();
  }

  public static Object unserialize(String value) {
    char head = value.charAt(0);
    int start = 1;
    int end = findEnd(value, start);
    String data = value.substring(start, end);
    switch(head) {
      case 'N':
        return unserializeNull();
      case 'b':
        return unserializeBoolean(data);
      case 'i':
        return unserializeInteger(data);
      case 'd':
        return unserializeDouble(data);
      case 's':
        return unserializeString(data);
      case 'a':
        return unserializeMap(data);
      default:
        return null;
    }
  }

}
