package im.grusis.mkb.core.repository;

import im.grusis.mkb.core.repository.model.MkbAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 下午10:03
 */
@Repository
public class AccountRepository extends MkbRepository<MkbAccount> {

  private static final Logger Log = LoggerFactory.getLogger(AccountRepository.class);

  public AccountRepository() {
    super("accounts", MkbAccount.class);
  }

  public MkbAccount getAccount(String username) {
    return read(username);
  }

  public void deleteAccount(String username, boolean backup) {
    remove(username, backup);
  }

  public void createOrUpdateAccount(MkbAccount mkbAccount) {
    String index = mkbAccount.getUsername();
    if(index == null || index.isEmpty()) {
      Log.error("Cannot create or update account with no username.");
      return;
    }
    write(index, mkbAccount, true);
  }

}
