package im.grusis.mkb;

import java.lang.reflect.Array;
import java.util.*;

/**
 * User: Mothership
 * Date: 13-5-13
 * Time: 下午10:23
 */
public class Serializer {

  public int p = 0;
  public Map<Integer, Object> hashMap = new HashMap<Integer, Object>();
  public int hv = 1;

  public boolean inHashMap(Object object) {
    return hashMap.values().contains(object);
  }

  public String serializeNull() {
    return "N;";
  }

  public String serializeBoolean(boolean value) {
    return value ? "b:1;" : "b:0;";
  }

  public String serializeInteger(int value) {
    return "i:" + value + ";";
  }

  public String serializeDouble(double value) {
    String append;
    if(Double.isNaN(value)) {
      append = "NAN";
    } else if(value == Double.POSITIVE_INFINITY) {
      append = "INF";
    } else if(value == Double.NEGATIVE_INFINITY) {
      append = "-INF";
    } else {
      append = Double.toString(value);
    }
    return "d:" + append + ";";
  }

  public String serializeString(String value) {
    return "s:" + value.length() + ":" + value + ";";
  }

  public String serializeDate(Date value) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(value);
    return "O:11:\"PHPRPC_Date\":7:{" + "s:4:\"year\";" + serializeInteger(calendar.get(Calendar.YEAR)) + "s:5:\"month\";" + serializeInteger(calendar.get(Calendar.MONTH) + 1) + "s:3:\"day\";" + serializeInteger(calendar.get(Calendar.DAY_OF_MONTH)) + "s:4:\"hour\";" + serializeInteger(calendar.get(Calendar.HOUR_OF_DAY)) + "s:6:\"minute\";" + serializeInteger(calendar.get(Calendar.MINUTE)) + "s:6:\"second\";" + serializeInteger(calendar.get(Calendar.SECOND)) + "s:11:\"millisecond\";" + serializeInteger(calendar.get(Calendar.MILLISECOND)) + "}";
  }

  public String serializeMap(Map map) {
    StringBuilder stringBuilder = new StringBuilder();
    Set entries = map.entrySet();
    stringBuilder.append("a:").append(map.size()).append(":{");
    String key;
    Object value;
    for(Object entry : entries) {
      key = ((Map.Entry)entry).getKey().toString();
      value = ((Map.Entry)entry).getValue();
      stringBuilder.append(serializeString(key)).append(serialize(value));
    }
    return stringBuilder.append("}").toString();
  }

  public String serializeList(List list) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("a:").append(list.size()).append(":{");
    int count = 0;
    for(Object object : list) {
      stringBuilder.append(serializeInteger(count++)).append(serialize(object));
    }
    return stringBuilder.append("}").toString();
  }

  public String serializeArray(Object[] array) {
    return serializeList(Arrays.asList(array));
  }

  public String serialize(Object o) {
    if(o == null) {
      return serializeNull();
    }
    if(o instanceof Boolean) {
      return serializeBoolean((Boolean)o);
    }
    if(o instanceof Integer) {
      return serializeInteger((Integer)o);
    }
    if(o instanceof Double) {
      return serializeDouble((Double)o);
    }
    if(o instanceof Date) {
      return serializeDate((Date)o);
    }
    if(o instanceof Map) {
      return serializeMap((Map)o);
    }
    if(o instanceof List) {
      return serializeList((List)o);
    }
    if(o instanceof Object[]) {
      return serializeArray((Object[]) o);
    }
    return o.toString();
  }

}
