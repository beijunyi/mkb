package im.grusis.mkb.config;

import im.grusis.mkb.eco.profiles.EcoProfile;
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
@PropertySource("classpath:/profile.properties")
public class ProfileConfig {

  @Autowired private Environment env;

  @Bean(name = "default")
  public EcoProfile defaultProfile() {
    return null;
  }

}
