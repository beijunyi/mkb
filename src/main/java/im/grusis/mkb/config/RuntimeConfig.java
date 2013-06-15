package im.grusis.mkb.config;

import java.util.Date;
import java.util.TimeZone;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * User: Junyi BEI
 * Date: 18/01/2013
 * Time: 22:58
 */
@Configuration
@PropertySource("classpath:/mkb.properties")
@Import({ControllerConfig.class, RepositoryConfig.class, ServiceConfig.class, EmulatorConfig.class, DictionaryConfig.class, BotConfig.class, EcoSystemConfig.class, ProfileConfig.class})
public class RuntimeConfig {

  public static final Logger Log = LoggerFactory.getLogger(RuntimeConfig.class);

  @Value("${timezone}") private String timezone;

  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone(timezone));
    Log.info("Timezone is set to {}, the current time is {}", timezone, new Date().toString());
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
