package im.grusis.mkb.eco.filter;

import im.grusis.mkb.internal.MkbAccount;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午8:56
 */
public interface AccountFilter {
  public boolean accept(MkbAccount account);
}
