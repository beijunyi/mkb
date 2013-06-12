package im.grusis.mkb.run;

import im.grusis.mkb.config.RuntimeConfig;
import im.grusis.mkb.emulator.emulator.AutomatedServiceEngine;
import im.grusis.mkb.emulator.emulator.MkbEmulator;
import im.grusis.mkb.util.MacAddressHelper;
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

    AutomatedServiceEngine ase = ctx.getBean(AutomatedServiceEngine.class);
    MkbEmulator emulator = ctx.getBean(MkbEmulator.class);
//    emulator.webLogin("urnmso90377", "mewhome123", MacAddressHelper.getMacAddress());
//    emulator.gamePassportLogin("urnmso90377");
//    emulator.gameGetUserInfo("urnmso90377", true);
    ase.clearMaze("urnmso90377", 8, 2, 0);
  }
}
