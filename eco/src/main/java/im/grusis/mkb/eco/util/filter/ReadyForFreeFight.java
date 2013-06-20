package im.grusis.mkb.eco.util.filter;

import im.grusis.mkb.eco.util.filter.common.CompareOperator;
import im.grusis.mkb.eco.util.filter.common.NumericProperty;
import im.grusis.mkb.eco.util.filter.common.NumericPropertyFilter;

/**
 * User: Mothership
 * Date: 13-6-19
 * Time: 下午11:28
 */
public class ReadyForFreeFight extends NumericPropertyFilter {
  public ReadyForFreeFight() {
    super(NumericProperty.Ticket, CompareOperator.GreaterThanOrEqualTo, 11);
  }
}
