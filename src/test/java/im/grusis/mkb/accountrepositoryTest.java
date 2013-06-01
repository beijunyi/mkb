package im.grusis.mkb;

import im.grusis.mkb.internal.MkbAccount;
import im.grusis.mkb.repository.AccountRepository;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: Mothership
 * Date: 13-5-30
 * Time: 下午9:59
 */
public class accountrepositoryTest {

  @Test
  public void readWriteTest() {
    MkbAccount account = new MkbAccount();
    account.setUsername("abc");
    account.setPassword("123");
    AccountRepository ar = new AccountRepository();
    ar.createOrUpdateAccount(account);
    MkbAccount ab = new AccountRepository().getAccount("abc");
    Assert.assertTrue(EqualsBuilder.reflectionEquals(ab, account));
  }
}
