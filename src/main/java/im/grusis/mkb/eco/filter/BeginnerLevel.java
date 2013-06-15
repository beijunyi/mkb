package im.grusis.mkb.eco.filter;

import im.grusis.mkb.eco.filter.common.CompareOperator;
import im.grusis.mkb.eco.filter.common.NumericProperty;
import im.grusis.mkb.eco.filter.common.NumericPropertyFilter;

/**
 * User: Mothership
 * Date: 13-6-15
 * Time: 下午1:10
 */
public class BeginnerLevel extends NumericPropertyFilter {

  public BeginnerLevel() {
    super(NumericProperty.Level, CompareOperator.LessThanOrEqualTo, 2);
  }

}
