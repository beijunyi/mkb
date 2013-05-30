package im.grusis.mkb.repository;

import im.grusis.mkb.internal.MkAccount;
import org.springframework.stereotype.Repository;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 下午10:03
 */
@Repository
public class AccountRepository extends MkbRepository {

  public MkAccount getAccount(String username) {
    return null;
  }

  public void addAccount(MkAccount mkAccount) {

  }

}
