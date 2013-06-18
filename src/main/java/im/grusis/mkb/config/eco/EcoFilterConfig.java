package im.grusis.mkb.config.eco;

import im.grusis.mkb.eco.filter.BeginnerLevel;
import im.grusis.mkb.eco.filter.HasReward;
import im.grusis.mkb.eco.filter.MissingFreeFightChip;
import im.grusis.mkb.eco.filter.common.All;
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


}
