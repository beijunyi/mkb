package im.grusis.mkb.eco.filter.common;

import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.eco.filter.AccountFilter;

/**
 * User: Mothership
 * Date: 13-6-15
 * Time: 上午12:28
 */
public class All implements AccountFilter {
  @Override
  public boolean accept(MkbAccount account) {
    return true;
  }
}
