package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:11
 */
public class DefaultProfile extends EcoProfile<DefaultProfile> {

  public static final String Prefix = "default.";

  public DefaultProfile(Environment environment, String root, DefaultProfile defaultProfile) {
    super(environment, root, defaultProfile);
  }
}
