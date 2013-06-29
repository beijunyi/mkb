package im.grusis.mkb.core.config;

import im.grusis.mkb.core.MkbCoreMarker;
import im.grusis.mkb.core.emulator.model.GameVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

/**
 * User: Junyi BEI
 * Date: 18/01/2013
 * Time: 22:58
 */
@Configuration
@PropertySource("classpath:/emulator.properties")
@ComponentScan(basePackageClasses = MkbCoreMarker.class)
public class EmulatorConfig {

  public static final String PLATFORM = "platform";
  public static final String LANGUAGE = "language";
  public static final String VERSION_CLIENT = "versionClient";
  public static final String VERSION_BUILD = "versionBuild";
  public static final String MAP_MAX = "mapMax";

  @Autowired Environment env;

  @Bean
  public GameVersion getGameVersion() {
    return new GameVersion(env.getProperty(PLATFORM),
                            env.getProperty(LANGUAGE),
                            env.getProperty(VERSION_CLIENT),
                            env.getProperty(VERSION_BUILD),
                            env.getProperty(MAP_MAX, Integer.class));
  }

}
