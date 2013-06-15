package im.grusis.mkb.eco;

import im.grusis.mkb.emulator.emulator.MkbEmulator;
import im.grusis.mkb.emulator.emulator.core.model.basic.UserInfo;
import im.grusis.mkb.exception.MkbException;
import im.grusis.mkb.internal.MkbAccount;
import im.grusis.mkb.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * User: Mothership
 * Date: 13-6-6
 * Time: 下午8:16
 */
@Component
@PropertySource("classpath:/eco.properties")
public class EcoSystemMaster {

  private static final Logger Log = LoggerFactory.getLogger(EcoSystemMaster.class);

  @Autowired MkbEmulator emulator;
  @Autowired AccountService accountService;

  @Value("${system.size}")

  @Scheduled(cron = "0 1/10 * * * *")
  public void addEnergy() throws MkbException {
    Iterable<MkbAccount> mkbAccounts = accountService.getAll();
    for(MkbAccount account : mkbAccounts) {
      UserInfo userInfo = emulator.gameGetUserInfo(account.getUsername(), false);
      int energy = userInfo.getEnergy();
      if(energy < 50) {
        energy++;
        userInfo.setEnergy(energy);
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
    Iterable<MkbAccount> mkbAccounts = accountService.getAll();
    for(MkbAccount account : mkbAccounts) {
      emulator.gameGetUserChip(account.getUsername(), true);
    }
  }


  @Scheduled(fixedDelay = 10000)
  public void createNewAccount() throws MkbException {

  }

  @Scheduled(fixedDelay = 20000)
  public void completeInvite() throws MkbException {

  }





}
