package im.grusis.mkb.app;

import im.grusis.mkb.core.config.MkbCoreConfig;
import im.grusis.mkb.core.emulator.AutomatedServiceEngine;
import im.grusis.mkb.core.emulator.MkbEmulator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TTTT {

  public static void main(String[] args) throws Exception {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MkbCoreConfig.class);
    ctx.start();

//    EcoSystemConfig.KeepConfig scl = ctx.getBean(EcoSystemConfig.KeepConfig.class);

    String username = "urnmso90377";
//    String username = "tug100";
    AutomatedServiceEngine ase = ctx.getBean(AutomatedServiceEngine.class);
    MkbEmulator emulator = ctx.getBean(MkbEmulator.class);

//    emulator.webLogin(username, "mewhome123");
    emulator.gameGetBossUpdate(username);

  }
}
