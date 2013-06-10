package im.grusis.mkb.run;

import im.grusis.mkb.config.RuntimeConfig;
import im.grusis.mkb.emulator.emulator.AutomatedServiceEngine;
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

    AutomatedServiceEngine ase = ctx.getBean(AutomatedServiceEngine.class);
    ase.clearMaze("urnmso90377", 8, 2, 0);
  }
}
