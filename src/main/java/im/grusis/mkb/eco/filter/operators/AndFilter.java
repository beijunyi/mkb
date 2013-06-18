package im.grusis.mkb.eco.filter.operators;

import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.eco.filter.AccountFilter;

/**
 * User: Mothership
 * Date: 13-6-8
 * Time: 上午2:09
 */
public class AndFilter implements AccountFilter {

  private AccountFilter[] filters;

  public AndFilter(AccountFilter... filters) {
    this.filters = filters;
  }

  @Override
  public boolean accept(MkbAccount account) {
    for(AccountFilter filter : filters) {
      if(!filter.accept(account)) {
        return false;
      }
    }
    return true;
  }
}
