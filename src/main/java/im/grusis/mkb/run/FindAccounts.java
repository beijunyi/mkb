package im.grusis.mkb.run;

import java.util.Collection;

import im.grusis.mkb.config.RuntimeConfig;
import im.grusis.mkb.internal.MkbAccount;
import im.grusis.mkb.internal.filters.CardQualityFilter;
import im.grusis.mkb.service.AccountService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * User: Mothership
 * Date: 13-6-7
 * Time: 下午11:46
 */
public class FindAccounts {

  public static void main(String[] args) {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(RuntimeConfig.class);
    ctx.start();

    AccountService as = ctx.getBean(AccountService.class);
    Collection<MkbAccount> accounts = as.findAll(true, new CardQualityFilter(4, 2));
    for(MkbAccount account : accounts) {
//      System.out.println(account.getUserInfo() + "")
    }
  }
}
