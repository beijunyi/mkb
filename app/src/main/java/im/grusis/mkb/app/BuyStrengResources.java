package im.grusis.mkb.app;

import im.grusis.mkb.core.config.MkbCoreConfig;
import im.grusis.mkb.core.emulator.AutomatedServiceEngine;
import im.grusis.mkb.core.emulator.EmulatorShop;
import im.grusis.mkb.core.emulator.ItemCode;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * User: Mothership
 * Date: 13-6-20
 * Time: 下午11:13
 */
public class BuyStrengResources {

  public static void main(String[] args) throws Exception{
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MkbCoreConfig.class);
    ctx.start();

    String username = "urnmso90377";
    AutomatedServiceEngine ase = ctx.getBean(AutomatedServiceEngine.class);
    EmulatorShop shop = ctx.getBean(EmulatorShop.class);

    for(int i = 0; i < 100; i++) {
      shop.gamePurchase(username, ItemCode.Coins);
    }

  }
}
