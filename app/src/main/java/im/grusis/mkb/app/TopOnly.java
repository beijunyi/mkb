package im.grusis.mkb.app;

import im.grusis.mkb.core.config.MkbCoreConfig;
import im.grusis.mkb.core.emulator.AutomatedServiceEngine;
import im.grusis.mkb.core.emulator.EmulatorMaze;
import im.grusis.mkb.core.emulator.EmulatorUser;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TopOnly {

  public static void main(String[] args) throws Exception {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MkbCoreConfig.class);
    ctx.start();


    String username = "tug100";

    AutomatedServiceEngine ase = ctx.getBean(AutomatedServiceEngine.class);
    EmulatorMaze maze = ctx.getBean(EmulatorMaze.class);
    EmulatorUser user = ctx.getBean(EmulatorUser.class);

//    core.login("tug100", "mewhome123", MacAddressHelper.getMacAddress());
    user.getUserInfo(username, true);
//    ase.clearCounterAttacks(username, 5);
    maze.show(username, 7, true);
    ase.clearMaze(username,7, 2, true, 0, true);
  }
}
