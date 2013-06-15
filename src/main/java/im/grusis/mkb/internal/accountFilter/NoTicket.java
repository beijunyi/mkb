package im.grusis.mkb.internal.accountFilter;

import im.grusis.mkb.internal.accountFilter.common.CompareOperator;
import im.grusis.mkb.internal.accountFilter.common.NumericProperty;
import im.grusis.mkb.internal.accountFilter.common.NumericPropertyFilter;

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
