package im.grusis.mkb.eco;

import im.grusis.mkb.core.emulator.EmulatorUser;
import im.grusis.mkb.core.exception.MkbException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import im.grusis.mkb.eco.util.filter.HasReward;
import im.grusis.mkb.eco.util.filter.ShallDoFreeFight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * User: Mothership
 * Date: 13-6-6
 * Time: 下午8:16
 */
@Component
public class ActionScheduler {

  private static final Logger Log = LoggerFactory.getLogger(ActionScheduler.class);


//  @Autowired BeginnerConfigList beginnerConfigList;

  @Autowired AccountService accountService;
  @Autowired EmulatorUser user;

  @Autowired HasReward hasReward;
  @Autowired ShallDoFreeFight shallDoFreeFight;


  @Scheduled(cron = "10 43 * * * *")
  public void claimReward() throws MkbException {
    Iterable<MkbAccount> accounts = accountService.findAll(hasReward);
    for(MkbAccount account : accounts) {
      user.getUserSalary(account.getUsername());
    }
  }

  @Scheduled(cron = "20 4/11 * * * *")
  public void chipFreeFight() throws MkbException {
    Iterable<MkbAccount> accounts = accountService.findAll(shallDoFreeFight);

  }

  @Scheduled(fixedDelay = 10 * 60 * 1000)
  public void clearBadBeginner() throws MkbException {
//    Iterable<MkbAccount> accounts = accountService.findAll();

  }


  @Scheduled(fixedDelay = 10 * 1000)
  public void createNewAccount() throws MkbException {

  }

  @Scheduled(fixedDelay = 20 * 1000)
  public void completeInvite() throws MkbException {

  }







}
