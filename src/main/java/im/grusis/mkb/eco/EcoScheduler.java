package im.grusis.mkb.eco;

import im.grusis.mkb.eco.configs.BeginnerConfigList;
import im.grusis.mkb.eco.filter.HasReward;
import im.grusis.mkb.eco.filter.MissingFreeFightChip;
import im.grusis.mkb.core.emulator.MkbEmulator;
import im.grusis.mkb.core.emulator.game.model.basic.UserInfo;
import im.grusis.mkb.exception.MkbException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
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
public class EcoScheduler {

  private static final Logger Log = LoggerFactory.getLogger(EcoScheduler.class);

  @Autowired MkbEmulator emulator;
  @Autowired AccountService accountService;
  @Autowired BeginnerConfigList beginnerConfigList;

  @Autowired HasReward hasReward;
  @Autowired MissingFreeFightChip missingFreeFightChip;

  @Scheduled(cron = "0 1/10 * * * *")
  public void addEnergy() throws MkbException {

    Iterable<MkbAccount> mkbAccounts = accountService.getAll();
    for(MkbAccount account : mkbAccounts) {
      UserInfo userInfo = emulator.gameGetUserInfo(account.getUsername(), false);
      if(userInfo != null) {
        int energy = userInfo.getEnergy();
        if(energy < 50) {
          energy++;
          userInfo.setEnergy(energy);
        }
      }
    }
  }

  @Scheduled(cron = "0 1 12,18 * * *")
  public void rewardEnergy() throws MkbException {
    Iterable<MkbAccount> mkbAccounts = accountService.getAll();
    for(MkbAccount account : mkbAccounts) {
      UserInfo userInfo = emulator.gameGetUserInfo(account.getUsername(), false);
      int energy = userInfo.getEnergy();
      energy += 20;
      if(energy > 70) {
        energy = 70;
      }
      userInfo.setEnergy(energy);
    }
  }


  @Scheduled(cron = "0 0 13,21 * * *")
  public void bossFight() throws MkbException {

  }

  @Scheduled(cron = "0 2 0/2 * * *")
  public void refreshUserInfo() throws MkbException {
    Iterable<MkbAccount> mkbAccounts = accountService.getAll();
    for(MkbAccount account : mkbAccounts) {
      emulator.gameGetUserInfo(account.getUsername(), true);
    }
  }

  @Scheduled(cron = "0 12 0/2 * * *")
  public void refreshCardGroup() throws MkbException {
    Iterable<MkbAccount> mkbAccounts = accountService.getAll();
    for(MkbAccount account : mkbAccounts) {
      emulator.gameGetUserCards(account.getUsername(), true);
    }
  }

  @Scheduled(cron = "0 22 0/4 * * *")
  public void refreshMapStage() throws MkbException {
    Iterable<MkbAccount> mkbAccounts = accountService.getAll();
    for(MkbAccount account : mkbAccounts) {
      emulator.gameGetUserMapStages(account.getUsername(), true);
    }
  }

  @Scheduled(cron = "0 32 0/4 * * *")
  public void refreshChip() throws MkbException {
    Iterable<MkbAccount> accounts = accountService.getAll();
    for(MkbAccount account : accounts) {
      emulator.gameGetUserChip(account.getUsername(), true);
    }
  }

  @Scheduled(fixedRate = 60 * 60 * 1000)
  public void claimReward() throws MkbException {
    Iterable<MkbAccount> accounts = accountService.findAll(hasReward);
    for(MkbAccount account : accounts) {
      emulator.gameFetchSalary(account.getUsername());
    }
  }

  @Scheduled(fixedRate = 10 * 60 * 1000)
  public void chipFreeFight() throws MkbException {
    Iterable<MkbAccount> accounts = accountService.findAll(missingFreeFightChip);
//    for()
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
