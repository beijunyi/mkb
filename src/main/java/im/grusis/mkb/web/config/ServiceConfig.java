package im.grusis.mkb.web.config;

import im.grusis.mkb.web.service.MkbService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * User: Junyi BEI
 * Date: 18/01/2013
 * Time: 22:58
 */
@Configuration
@ComponentScan(basePackageClasses = MkbService.class)
public class ServiceConfig {
}
