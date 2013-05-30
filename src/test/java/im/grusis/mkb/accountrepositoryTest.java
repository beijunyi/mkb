package im.grusis.mkb;

import im.grusis.mkb.internal.MkAccount;
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
    MkAccount account = new MkAccount();
    account.setUsername("abc");
    account.setPassword("123");
    account.setInviteCode("gggggg");
    account.setInviteCount(0);
    AccountRepository ar = new AccountRepository();
    ar.createOrUpdateAccount(account);
    MkAccount ab = new AccountRepository().getAccount("abc");
    Assert.assertTrue(EqualsBuilder.reflectionEquals(ab, account));
  }
}
