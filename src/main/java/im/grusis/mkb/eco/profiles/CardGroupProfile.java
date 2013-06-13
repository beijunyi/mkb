package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:10
 */
public class CardGroupProfile extends Profile<CardGroupProfile> {

  public static final String Prefix = "cardGroup.";
  public static final String Main = "main";
  public static final String Boss = "boss";
  public static final String Thief = "thief";

  private int main;
  private int boss;
  private int thief;

  public CardGroupProfile(Environment environment, String root, CardGroupProfile defaultProfile) {
    super(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, CardGroupProfile defaultProfile) {
    setMain(environment.getProperty(root + Prefix + Main, Integer.class, defaultProfile == null ? null : defaultProfile.getMain()));
    setBoss(environment.getProperty(root + Prefix + Boss, Integer.class, defaultProfile == null ? null : defaultProfile.getBoss()));
    setThief(environment.getProperty(root + Prefix + Boss, Integer.class, defaultProfile == null ? null : defaultProfile.getThief()));
  }

  public int getMain() {
    return main;
  }

  public void setMain(int main) {
    this.main = main;
  }

  public int getBoss() {
    return boss;
  }

  public void setBoss(int boss) {
    this.boss = boss;
  }

  public int getThief() {
    return thief;
  }

  public void setThief(int thief) {
    this.thief = thief;
  }
}