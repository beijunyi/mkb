package im.grusis.mkb.app;

import java.util.concurrent.ExecutorService;

import im.grusis.mkb.core.config.MkbCoreConfig;
import im.grusis.mkb.core.emulator.AutomatedServiceEngine;
import im.grusis.mkb.core.emulator.EmulatorBoss;
import im.grusis.mkb.eco.bot.BossBot;
import im.grusis.mkb.eco.config.MkbEcoConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TTTT {

  public static void main(String[] args) throws Exception {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MkbCoreConfig.class, MkbEcoConfig.class);
    ctx.start();

//    EcoSystemConfig.KeepConfig scl = ctx.getBean(EcoSystemConfig.KeepConfig.class);

    String username = "urnmso90377";
//    String username = "tug100";
    EmulatorBoss ase = ctx.getBean(EmulatorBoss.class);
    ExecutorService es = ctx.getBean(ExecutorService.class);
    es.submit(new BossBot("tug100", ase));

  }
}
