package im.grusis.mkb.eco.util.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Mothership
 * Date: 13-6-29
 * Time: 下午6:09
 */
public class BasicDictRecord implements DictRecord<BasicDict> {
  private Map<String, Integer> counts;

  @Override
  public void update(BasicDict dict) {
    if(counts == null) {
      counts = new HashMap<String, Integer>();
    }
    int count = dict.getCount();
    counts.put(dict.getPrefix(), count);
  }

  @Override
  public void apply(BasicDict dict) {
    if(counts != null) {
      String prefix = dict.getPrefix();
      Integer count = counts.get(prefix);
      if(count != null) {
        dict.setCount(count);
      }
    }
  }
}
