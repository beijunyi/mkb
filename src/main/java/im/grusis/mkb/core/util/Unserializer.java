package im.grusis.mkb.core.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: beij
 * Date: 14/05/13
 * Time: 15:59
 */
public class Unserializer {

  private static final Logger Log = LoggerFactory.getLogger(Unserializer.class);

  public static final String StringPattern = "s:(\\d+):\"([\\w\\W]*)\";";
  public static final String ArrayPattern = "a:(\\d+):\\{([^}]*)}";
  public static final String ValuePairPattern = "%([^%]*)%%([^%]*)%";

  public static List<Object> Unserialize(String string) {
    Map<Object, Object> refs = new HashMap<Object, Object>();
//    string = string.replace("{\"", "{'").replace("\":", "':").replace(":\"", ":'").replace("\",", "',").replace(",\"", ",'").replace("\"}", "'}");
    Pattern sp = Pattern.compile(StringPattern);
    int length;
    Matcher sm;
    String block, value, hash;
    while((sm = sp.matcher(string)).find()) {
      block = sm.group(0);
      length = Integer.parseInt(sm.group(1));
      value = sm.group(2);
      Log.debug("Unserialize string {}", block);
      if(length < value.length()) {
        int diff = value.length() - length;
        block = block.substring(0, block.length() - diff);
        value = value.substring(0, length);
      }
      hash = Integer.toHexString(block.hashCode());
      refs.put(hash, value);
      string = string.replace(block, '%' + hash + '%');
    }
    Pattern ap = Pattern.compile(ArrayPattern);
    Pattern vpp = Pattern.compile(ValuePairPattern);
    Matcher apm, vppm;
    Map<Object, Object> arrayMap;
    String pairKey, pairValue;
    while((apm = ap.matcher(string)).find()) {
      block = apm.group(0);
      length = Integer.parseInt(apm.group(1));
      value = apm.group(2);
      Log.debug("Unserialize array {}", block);
      arrayMap = new LinkedHashMap<Object, Object>();
      hash = Integer.toHexString(block.hashCode());
      refs.put(hash, arrayMap);
      string = string.replace(block, '%' + hash + '%');
      while((vppm = vpp.matcher(value)).find()) {
        block = vppm.group(0);
        pairKey = vppm.group(1);
        pairValue = vppm.group(2);
        arrayMap.put(refs.get(pairKey), refs.get(pairValue));
        refs.remove(pairKey);
        refs.remove(pairValue);
        value = value.replace(block, "");
      }
      if(length != arrayMap.size())
        Log.warn("Detected unmatched array size when unserializing {}. Expected size: {} Actual size: {}", block, length, arrayMap.size());
    }
    return new ArrayList<Object>(refs.values());
  }

  public static String UnserializeString(String string) {
    List<Object> list = Unserialize(string);
    if(list.size() != 1) {
      Log.warn("Unserializing {} results unexpected number of element(s). Expected: 1 Actual: {}", string, list.size());
    }
    for(Object o : list) {
      if(o instanceof String) {
        return (String)o;
      }
    }
    return null;
  }

  public static Map UnserializeMap(String string) {
    List<Object> list = Unserialize(string);
    if(list.size() != 1) {
      Log.warn("Unserializing {} results unexpected number of element(s). Expected: 1 Actual: {}", string, list.size());
    }
    for(Object o : list) {
      if(o instanceof Map) {
        return (Map)o;
      }
    }
    return null;
  }

  public static LinkedHashMap JsonToMap(String string) {
    return new Gson().fromJson(string, LinkedHashMap.class);
  }
}
