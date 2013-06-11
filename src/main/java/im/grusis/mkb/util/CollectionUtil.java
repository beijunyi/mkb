package im.grusis.mkb.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: Mothership
 * Date: 13-6-10
 * Time: 下午11:49
 */
public class CollectionUtil {
  public static<T> Map<T, Integer> instanceCount(Iterable<T> collection) {
    Map<T, Integer> ret = new LinkedHashMap<T, Integer>();
    for(T instance : collection) {
      Integer count = ret.get(instance);
      if(count == null) {
        ret.put(instance, 1);
      } else {
        ret.put(instance, count + 1);
      }
    }
    return ret;
  }
}
