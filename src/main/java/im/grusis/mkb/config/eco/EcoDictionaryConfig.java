package im.grusis.mkb.config.eco;

import im.grusis.mkb.core.util.dictionary.DictionaryManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * User: Junyi BEI
 * Date: 18/01/2013
 * Time: 22:58
 */
@Configuration
@ComponentScan(basePackageClasses = DictionaryManager.class)
public class EcoDictionaryConfig {
}
