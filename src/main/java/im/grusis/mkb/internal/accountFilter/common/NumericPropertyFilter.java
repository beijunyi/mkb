package im.grusis.mkb.internal.accountFilter.common;

import im.grusis.mkb.internal.MkbAccount;
import im.grusis.mkb.internal.accountFilter.AccountFilter;

/**
 * User: Mothership
 * Date: 13-6-11
 * Time: 上午12:01
 */
public class NumericPropertyFilter implements AccountFilter {

  private NumericProperty property;
  private CompareOperator compare;
  private long target;

  public NumericPropertyFilter(NumericProperty property, CompareOperator compare, long target) {
    this.property = property;
    this.compare = compare;
    this.target = target;
  }

  @Override
  public boolean accept(MkbAccount account) {
    long value;
    switch(property) {
      case Level:
        value = account.getLevel();
        break;
      case Gold:
        value = account.getGold();
        break;
      case Diamond:
        value = account.getDiamond();
        break;
      case Ticket:
        value = account.getTicket();
        break;
      default:
        return false;
    }
    switch(compare) {
      case LessThan:
        return value < target;
      case LessThanOrEqualTo:
        return value <= target;
      case EqualTo:
        return value == target;
      case GreaterThan:
        return value > target;
      case GreaterThanOrEqualTo:
        return value >= target;
      case NotEqualTo:
        return value != target;
      default:
        return false;
    }
  }
}
