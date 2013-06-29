package im.grusis.mkb.core.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import im.grusis.mkb.core.MkbCoreMarker;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * User: Junyi BEI
 * Date: 18/01/2013
 * Time: 22:58
 */
@Configuration
@PropertySource("classpath:/core.properties")
@ComponentScan(basePackageClasses = MkbCoreMarker.class)
@Import(EmulatorConfig.class)
public class MkbCoreConfig {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean(destroyMethod = "shutdown")
  public Executor getTaskExecutors() {
    return Executors.newScheduledThreadPool(20);
  }
}
