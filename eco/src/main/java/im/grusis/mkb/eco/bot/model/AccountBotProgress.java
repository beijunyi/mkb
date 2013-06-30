package im.grusis.mkb.eco.bot.model;

import java.util.ArrayList;
import java.util.List;

import im.grusis.mkb.core.repository.model.MkbAccount;

/**
 * User: Mothership
 * Date: 13-6-29
 * Time: 下午11:29
 */
public class AccountBotProgress {
  private List<MkbAccount> accounts = new ArrayList<MkbAccount>();
  private int total;
  private boolean finish = false;

  public AccountBotProgress(int total) {
    this.total = total;
  }

  public List<MkbAccount> getAccounts() {
    return accounts;
  }

  public int getTotal() {
    return total;
  }

  public boolean isFinish() {
    return finish;
  }

  public void addAccount(MkbAccount account) {
    accounts.add(account);
  }

  public void finish() {
    finish = true;
  }
}
