package im.grusis.mkb.eco.util.filter.common;

import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.util.AccountFilter;

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
