package im.grusis.mkb.config.eco;

import im.grusis.mkb.eco.profiles.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午10:58
 */
@Configuration
@PropertySource("classpath:/eco_profile.properties")
public class EcoProfileConfig {

  @Autowired private Environment env;

  @Bean
  public DefaultProfile defaultProfile() {
    return new DefaultProfile(env);
  }

  @Bean
  public CommonProfileMap commonProfile(DefaultProfile defaultProfile) {
    CommonProfileMap commonProfileMap = new CommonProfileMap();
    int count = 1;
    while(true) {
      if(env.containsProperty(EcoProfile.Profile + CommonProfile.Common + count)) {
        CommonProfile commonProfile = new CommonProfile(env, count++, defaultProfile);
        commonProfileMap.put(commonProfile.getName(), commonProfile);
      } else {
        break;
      }
    }
    return commonProfileMap;
  }
}
