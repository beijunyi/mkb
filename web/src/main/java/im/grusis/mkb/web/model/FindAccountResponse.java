package im.grusis.mkb.web.model;

import java.util.Collection;
import java.util.List;

/**
 * User: Mothership
 * Date: 13-6-25
 * Time: 下午9:18
 */
public class FindAccountResponse {
  private Collection<AccountView> accounts;

  public FindAccountResponse(List<AccountView> accounts) {
    this.accounts = accounts;
  }

  public Collection<AccountView> getAccounts() {
    return accounts;
  }
}
