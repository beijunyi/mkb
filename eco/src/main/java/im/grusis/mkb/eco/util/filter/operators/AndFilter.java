package im.grusis.mkb.eco.util.filter.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.util.AccountFilter;

/**
 * User: Mothership
 * Date: 13-6-8
 * Time: 上午2:09
 */
public class AndFilter implements AccountFilter {

  private List<AccountFilter> filters;

  public AndFilter(List<AccountFilter> filters) {
    this.filters = filters;
  }

  public AndFilter(AccountFilter... filters) {
    List<AccountFilter> accountFilters = new ArrayList<AccountFilter>();
    Collections.addAll(accountFilters, filters);
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
