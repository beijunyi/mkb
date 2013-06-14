package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:10
 */
public class CardGroupProfile extends Profile<CardGroupProfile> {

  public static final String CardGroup = "cardGroup.";
  public static final String Main = "main";
  public static final int MainDefault = 1;
  public static final String Boss = "boss";
  public static final int BossDefault = 1;
  public static final String Thief = "thief";
  public static final int ThiefDefault = 1;

  private int main;
  private int boss;
  private int thief;

  public CardGroupProfile(Environment environment, String root, CardGroupProfile defaultProfile) {
    super(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, CardGroupProfile defaultProfile) {
    setMain(environment.getProperty(root + CardGroup + Main, Integer.class, defaultProfile == null ? MainDefault : defaultProfile.getMain()));
    setBoss(environment.getProperty(root + CardGroup + Boss, Integer.class, defaultProfile == null ? BossDefault : defaultProfile.getBoss()));
    setThief(environment.getProperty(root + CardGroup + Boss, Integer.class, defaultProfile == null ? ThiefDefault : defaultProfile.getThief()));
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