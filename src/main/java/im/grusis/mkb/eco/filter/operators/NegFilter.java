package im.grusis.mkb.eco.filter.operators;

import im.grusis.mkb.internal.MkbAccount;
import im.grusis.mkb.eco.filter.AccountFilter;

/**
 * User: Mothership
 * Date: 13-6-8
 * Time: 上午2:09
 */
public class NegFilter implements AccountFilter {

  private AccountFilter filter;

  public NegFilter(AccountFilter filter) {
    this.filter = filter;
  }

  @Override
  public boolean accept(MkbAccount account) {
    return !filter.accept(account);
  }
}
