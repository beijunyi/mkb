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
public class OrFilter implements AccountFilter {

  private List<AccountFilter> filters;

  public OrFilter(List<AccountFilter> filters) {
    this.filters = filters;
  }

  public OrFilter(AccountFilter... accountFilters) {
    filters = new ArrayList<AccountFilter>();
    Collections.addAll(filters, accountFilters);
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
