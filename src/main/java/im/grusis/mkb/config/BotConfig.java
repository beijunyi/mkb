package im.grusis.mkb.config;

import im.grusis.mkb.emulator.bot.BotManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * User: Junyi BEI
 * Date: 18/01/2013
 * Time: 22:58
 */
@Configuration
@ComponentScan(basePackageClasses = BotManager.class)
public class BotConfig {
}
