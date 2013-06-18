package im.grusis.mkb.eco.filter.common;

import im.grusis.mkb.core.emulator.game.model.basic.UserInfo;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.eco.filter.AccountFilter;

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
    UserInfo userInfo = account.getUserInfo();
    if(userInfo == null) {
      return false;
    }
    switch(property) {
      case Level:
        value = userInfo.getLevel();
        break;
      case Gold:
        value = userInfo.getCoins();
        break;
      case Diamond:
        value = userInfo.getCash();
        break;
      case Ticket:
        value = userInfo.getTicket();
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
