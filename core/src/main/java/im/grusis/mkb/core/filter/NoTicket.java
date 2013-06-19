package im.grusis.mkb.core.filter;

import im.grusis.mkb.core.filter.common.CompareOperator;
import im.grusis.mkb.core.filter.common.NumericProperty;
import im.grusis.mkb.core.filter.common.NumericPropertyFilter;

/**
 * User: Mothership
 * Date: 13-6-15
 * Time: 下午1:10
 */
public class NoTicket extends NumericPropertyFilter {

  public NoTicket() {
    super(NumericProperty.Ticket, CompareOperator.EqualTo, 0);
  }

}
