package im.grusis.mkb.core.filter;

import im.grusis.mkb.core.filter.common.CompareOperator;
import im.grusis.mkb.core.filter.common.NumericProperty;
import im.grusis.mkb.core.filter.common.NumericPropertyFilter;

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
