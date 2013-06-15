package im.grusis.mkb.eco.filter.util;

import java.util.*;

import im.grusis.mkb.eco.filter.common.CompareOperator;
import im.grusis.mkb.emulator.emulator.core.model.basic.UserCardInfo;
import im.grusis.mkb.emulator.emulator.core.model.basic.UserCards;

/**
 * User: Mothership
 * Date: 13-6-15
 * Time: 下午11:05
 */
public class CardUtils {

  public static List<Integer> GetCardIdList(UserCards cards) {
    List<Integer> ret = new ArrayList<Integer>();
    for(UserCardInfo card : cards) {
      ret.add(card.getCardId());
    }
    return ret;
  }

  public static Map<Integer, Integer> GetCardCount(List<Integer> cardIdList) {
    Map<Integer, Integer> ret = new TreeMap<Integer, Integer>();
    for(int id : cardIdList) {
      Integer count = ret.get(id);
      if(count == null) {
        count = 1;
      } else {
        count++;
      }
      ret.put(id, count);
    }
    return ret;
  }

  public static Map<Integer, Integer> GetCardCount(UserCards cards) {
    return GetCardCount(GetCardIdList(cards));
  }

  public static boolean CompareCardCount(Map<Integer, Integer> source, Map<Integer, Integer> target, CompareOperator compare) {
    for(Map.Entry<Integer, Integer> node : target.entrySet()) {
      Integer value = source.get(node.getKey());
      if(value == null) {
        value = 0;
      }
      int threshold = node.getValue();
      switch(compare) {
        case LessThan:
          if(value >= threshold) {
            return false;
          }
          break;
        case LessThanOrEqualTo:
          if(value > threshold) {
            return false;
          }
          break;
        case EqualTo:
          if(value != threshold) {
            return false;
          }
          break;
        case GreaterThan:
          if(value <= threshold) {
            return false;
          }
          break;
        case GreaterThanOrEqualTo:
          if(value < threshold) {
            return false;
          }
          break;
        case NotEqualTo:
          if(value == threshold) {
            return false;
          }
          break;
        default:
          return false;
      }
    }
    return true;
  }
}
