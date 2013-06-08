package im.grusis.mkb.internal.accountFilter.operators;

import im.grusis.mkb.internal.MkbAccount;
import im.grusis.mkb.internal.accountFilter.AccountFilter;

/**
 * User: Mothership
 * Date: 13-6-8
 * Time: 上午2:09
 */
public class OrFilter implements AccountFilter {

  private AccountFilter[] filters;

  public OrFilter(AccountFilter... filters) {
    this.filters = filters;
  }

  @Override
  public boolean accept(MkbAccount account) {
    for(AccountFilter filter : filters) {
      if(filter.accept(account)) {
        return true;
      }
    }
    return false;
  }
}
