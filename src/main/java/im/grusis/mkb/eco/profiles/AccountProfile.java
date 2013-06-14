package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:24
 */
public class AccountProfile extends EcoProfile {

  public static final String Account = "account.";
  public static final String Common = ".common";

  private String username;

  public AccountProfile(Environment environment, int index, DefaultProfile defaultProfile, CommonProfileMap commonProfileMap) {
    String common = environment.getProperty(Profile + Account + index + Common);
    String root = Profile + Account + index;
    if(common != null) {
      read(environment, root, commonProfileMap.get(common));
    } else {
      read(environment, root, defaultProfile);
    }
  }

  public AccountProfile(Environment environment, String root, EcoProfile defaultProfile) {
    read(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, EcoProfile defaultProfile) {
    super.read(environment, root, defaultProfile);
    setUsername(environment.getProperty(root));
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
