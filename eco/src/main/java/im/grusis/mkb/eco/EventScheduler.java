package im.grusis.mkb.eco;

import java.util.Collection;

import im.grusis.mkb.core.emulator.EmulatorUser;
import im.grusis.mkb.core.emulator.game.model.basic.UserInfo;
import im.grusis.mkb.core.exception.MkbException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * User: Mothership
 * Date: 13-6-19
 * Time: 下午11:08
 */
@Component
public class EventScheduler {

  private static final Logger Log = LoggerFactory.getLogger(EventScheduler.class);

  @Autowired EmulatorUser user;
  @Autowired AccountService accountService;

  @Scheduled(cron = "0 1/10 * * * *")
  public void naturalEnergy() throws MkbException {
    Log.info("Sending natural energy to all accounts");
    Collection<MkbAccount> mkbAccounts = accountService.getAll();
    int count = 0;
    for(MkbAccount account : mkbAccounts) {
      UserInfo userInfo = user.getUserInfo(account.getUsername(), false);
      if(userInfo != null) {
        int energy = userInfo.getEnergy();
        if(energy < 50) {
          energy++;
          userInfo.setEnergy(energy);
          count++;
        }
      }
    }
    Log.info("Finished sending natural energy. {} of {} accounts have received 1 energy", count, mkbAccounts.size());
  }

  @Scheduled(cron = "0 1 12,18 * * *")
  public void subsidyEnergy() throws MkbException {
    Log.info("Sending subsidy energy to all accounts");
    Collection<MkbAccount> mkbAccounts = accountService.getAll();
    int count = 0;
    for(MkbAccount account : mkbAccounts) {
      UserInfo userInfo = user.getUserInfo(account.getUsername(), false);
      int energy = userInfo.getEnergy();
      if(energy < 70) {
        energy += 20;
        if(energy > 70) {
          energy = 70;
        }
        userInfo.setEnergy(energy);
        count++;
      }
    }
    Log.info("Finished sending natural energy. {} of {} accounts have received 1 energy", count, mkbAccounts.size());
  }

  @Scheduled(cron = "30 0 0 * * *")
  public void newDay() throws MkbException {

  }

  @Scheduled(cron = "0 0 13,21 * * *")
  public void bossFight() throws MkbException {

  }

  @Scheduled(cron = "0 50 19,21 * * *")
  public void legionFight() throws MkbException {

  }

}
