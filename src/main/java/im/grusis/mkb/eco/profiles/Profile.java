package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:31
 */
public abstract class Profile<T extends Profile> {

  public Profile(Environment environment, String root, T defaultProfile) {
    read(environment, root, defaultProfile);
  }

  public abstract void read(Environment environment, String root, T defaultProfile);
}
