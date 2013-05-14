package im.grusis.mkb;

import java.util.*;

/**
 * User: Mothership
 * Date: 13-5-13
 * Time: 下午10:23
 */
public class MUHE_Serializer {

  public int p = 0;
  public StringBuilder stringBuilder = new StringBuilder();
  public Map<Integer, Object> hashMap = new HashMap<Integer, Object>();
  public int hv = 1;



  public boolean inHashMap(Object object) {
    return hashMap.values().contains(object);
  }

  public void serializeNull() {
    stringBuilder.append("N;");
  }

  public void serializeBoolean(boolean value) {
    stringBuilder.append(value ? "b:1;" : "b:0;");
  }

  public void serializeInteger(int value) {
    stringBuilder.append("i:").append(value).append(";");
  }

  public void serializeDouble(double value) {
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
    stringBuilder.append("d:").append(append).append(";");
  }

  public void serializeString(String value) {
    stringBuilder.append("s:").append(value.length()).append(":").append(value).append(";");
  }

  public void serializeDate(Date value) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(value);
    stringBuilder.append("O:11:\"PHPRPC_Date\":7:{");
    stringBuilder.append("s:4:\"year\";");
    serializeInteger(calendar.get(Calendar.YEAR));
    stringBuilder.append("s:5:\"month\";");
    serializeInteger(calendar.get(Calendar.MONTH) + 1);
    stringBuilder.append("s:3:\"day\";");
    serializeInteger(calendar.get(Calendar.DAY_OF_MONTH));
    stringBuilder.append("s:4:\"hour\";");
    serializeInteger(calendar.get(Calendar.HOUR_OF_DAY));
    stringBuilder.append("s:6:\"minute\";");
    serializeInteger(calendar.get(Calendar.MINUTE));
    stringBuilder.append("s:6:\"second\";");
    serializeInteger(calendar.get(Calendar.SECOND));
    stringBuilder.append("s:11:\"millisecond\";");
    serializeInteger(calendar.get(Calendar.MILLISECOND));
    stringBuilder.append("}");
  }

  public void serializePointRef(String ref) {
    stringBuilder.append("R:").append(ref).append(";");
  }

  public void serializeRef(String ref) {
    stringBuilder.append("r:").append(ref).append(";");
  }

  public void serializeMap(Map map) {

  }

}
