package im.grusis.mkb.eco.config;

import im.grusis.mkb.eco.util.filter.*;
import im.grusis.mkb.eco.util.filter.common.All;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * User: Mothership
 * Date: 13-6-15
 * Time: 上午12:26
 */
@Configuration
@PropertySource("classpath:/eco_filter.properties")
public class EcoFilterConfig {

  @Bean
  public All all() {
    return new All();
  }

  @Bean
  public BeginnerLevel beginnerLevel() {
    return new BeginnerLevel();
  }

  @Bean
  public HasReward hasReward() {
    return new HasReward();
  }

  @Bean
  public MissingFreeFightChip missingFreeFightChip() {
    return new MissingFreeFightChip();
  }

  @Bean
  public ShallDoFreeFight shallDoFreeFight() {
    return new ShallDoFreeFight();
  }


}
