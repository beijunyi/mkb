package im.grusis.mkb.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * User: Junyi BEI
 * Date: 18/01/2013
 * Time: 22:58
 */
@Configuration
@Import({ServiceConfig.class, ControllerConfig.class})
public class RuntimeConfig {
}
