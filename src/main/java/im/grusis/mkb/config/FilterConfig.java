package im.grusis.mkb.config;

import im.grusis.mkb.internal.accountFilter.common.All;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * User: Mothership
 * Date: 13-6-15
 * Time: 上午12:26
 */
@Configuration
@PropertySource("classpath:/filter.properties")
public class FilterConfig {

  @Bean
  public All all() {
    return new All();
  }


}
