package im.grusis.mkb.core.util;

import im.grusis.mkb.core.repository.model.MkbAccount;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午8:56
 */
public interface AccountFilter {
  public boolean accept(MkbAccount account);
}
