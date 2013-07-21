package im.grusis.mkb.app;

import im.grusis.mkb.core.config.MkbCoreConfig;
import im.grusis.mkb.core.emulator.MkbEmulator;
import im.grusis.mkb.core.emulator.game.model.basic.EvolutionResult;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * User: Mothership
 * Date: 13-6-30
 * Time: 下午10:43
 */
public class StrengPreview {

  public static void main(String args[]) throws Exception {
    String username = "tug100";
    int type = 1;
    long target = 2086911;
    long source = 13516479;

    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MkbCoreConfig.class);
    ctx.start();
    MkbEmulator emulator = ctx.getBean(MkbEmulator.class);

    EvolutionResult result = emulator.evolution().evolve(username, target, source);
  }
}
