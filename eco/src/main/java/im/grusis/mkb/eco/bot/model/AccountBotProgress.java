package im.grusis.mkb.eco.bot.model;

import java.util.HashMap;
import java.util.Map;

import im.grusis.mkb.core.repository.model.MkbAccount;

/**
 * User: Mothership
 * Date: 13-6-29
 * Time: 下午11:29
 */
public class AccountBotProgress {
  private Map<String, MkbAccount> accounts = new HashMap<String, MkbAccount>();
  private int total;
  private boolean finish = false;

  public AccountBotProgress(int total) {
    this.total = total;
  }

  public Map<String, MkbAccount> getAccounts() {
    return accounts;
  }

  public int getTotal() {
    return total;
  }

  public boolean isFinish() {
    return finish;
  }

  public void addCount(MkbAccount account) {
    accounts.put(account.getUsername(), account);
  }

  public void finish() {
    finish = true;
  }
}
