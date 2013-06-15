package im.grusis.mkb.run;

import im.grusis.mkb.config.RuntimeConfig;
import im.grusis.mkb.emulator.emulator.AutomatedServiceEngine;
import im.grusis.mkb.emulator.emulator.MkbEmulator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * User: Mothership
 * Date: 13-6-7
 * Time: 下午11:46
 */
public class FindAccounts {

  public static void main(String[] args) throws Exception{
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(RuntimeConfig.class);
    ctx.start();

//    EcoSystemConfig.KeepConfig scl = ctx.getBean(EcoSystemConfig.KeepConfig.class);

    System.currentTimeMillis();


//    String username = "urnmso90377";
    String username = "tug100";
    AutomatedServiceEngine ase = ctx.getBean(AutomatedServiceEngine.class);
    MkbEmulator emulator = ctx.getBean(MkbEmulator.class);
//    emulator.webLogin("tug100", "mewhome123", MacAddressHelper.getMacAddress());
//    emulator.gameGetUserInfo(username, true);
//    ase.clearCounterAttacks(username, 5);
    ase.clearMaze(username,8, 2, true, 0);
    ase.clearMaze(username,7, 2, true, 0);
    ase.clearMaze(username,6, 2, true, 0);
    ase.collectAndSendEnergy(username, true, true);
    ase.clearCounterAttacks(username, 5);
    ase.clearMaze(username,8, 2, true, 0);
    ase.clearMaze(username,7, 2, true, 0);
    ase.clearMaze(username,6, 2, true, 0);
  }
}
