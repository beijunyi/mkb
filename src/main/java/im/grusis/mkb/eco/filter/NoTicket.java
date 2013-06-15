package im.grusis.mkb.eco.filter;

import im.grusis.mkb.eco.filter.common.CompareOperator;
import im.grusis.mkb.eco.filter.common.NumericProperty;
import im.grusis.mkb.eco.filter.common.NumericPropertyFilter;

/**
 * User: Mothership
 * Date: 13-6-15
 * Time: 下午1:10
 */
public class NoTicket extends NumericPropertyFilter {

  public NoTicket(NumericProperty property, CompareOperator compare, long target) {
    super(property, compare, target);
  }

}
