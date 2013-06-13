package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:24
 */
public class AccountProfile extends EcoProfile<AccountProfile> {

  public static final String Prefix = "account.";

  private String username;

  public AccountProfile(Environment environment, String root, AccountProfile defaultProfile) {
    super(environment, root, defaultProfile);
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
