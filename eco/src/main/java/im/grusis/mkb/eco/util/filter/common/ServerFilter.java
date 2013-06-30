package im.grusis.mkb.eco.util.filter.common;

import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.util.AccountFilter;

/**
 * User: Mothership
 * Date: 13-6-30
 * Time: 下午3:12
 */
public class ServerFilter implements AccountFilter {
  private String server;

  public ServerFilter(String server) {
    this.server = server;
  }

  @Override
  public boolean accept(MkbAccount account) {
    String accountServer = account.getServer();
    return accountServer != null && accountServer.equals(server);
  }
}
