package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:11
 */
public class CommonProfile extends EcoProfile<CommonProfile> {

  public static final String Prefix = "common.";

  private String name;

  public CommonProfile(Environment environment, String root, CommonProfile defaultProfile) {
    super(environment, root, defaultProfile);
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
