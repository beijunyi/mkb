package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:11
 */
public class DefaultProfile extends EcoProfile {

  public static final String Default = "default.";

  public DefaultProfile(Environment environment) {
    this(environment, Profile + Default, null);
  }

  public DefaultProfile(Environment environment, String root, DefaultProfile defaultProfile) {
    read(environment, root, defaultProfile);
  }
}
