package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:11
 */
public class CommonProfile extends EcoProfile {

  public static final String Common = "common.";

  private String name;

  public CommonProfile(Environment environment, int index, DefaultProfile defaultProfile) {
    this(environment, Profile + Common + index, defaultProfile);
  }

  public CommonProfile(Environment environment, String root, DefaultProfile defaultProfile) {
    read(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, EcoProfile defaultProfile) {
    super.read(environment, root, defaultProfile);
    setName(environment.getProperty(root));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
