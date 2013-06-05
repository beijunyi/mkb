package im.grusis.mkb;

import im.grusis.mkb.config.RuntimeConfig;
import im.grusis.mkb.emulator.bot.BotManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 下午8:22
 */
public class TEMP2 {

//  @Test
  public void t() {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(RuntimeConfig.class);
    ctx.start();

    BotManager bm = ctx.getBean(BotManager.class);
    bm.angelBot("mkbangel", "使徒", 1, 331, "45ekat");

    System.currentTimeMillis();
  }

  public static void main(String args[]) {
    new TEMP2().t();
  }
}
