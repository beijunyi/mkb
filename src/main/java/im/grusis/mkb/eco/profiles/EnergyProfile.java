package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:08
 */
public class EnergyProfile extends Profile<EnergyProfile> {

  public static final String Prefix = "energy.";
  public static final String Send = "send";
  public static final String Receive = "receive";

  private boolean send;
  private boolean receive;

  public EnergyProfile(Environment environment, String root, EnergyProfile defaultProfile) {
    super(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, EnergyProfile defaultProfile) {
    setSend(environment.getProperty(root + Prefix + Send, Boolean.class, defaultProfile == null ? null : defaultProfile.isSend()));
    setReceive(environment.getProperty(root + Prefix + Receive, Boolean.class, defaultProfile == null ? null : defaultProfile.isReceive()));
  }

  public boolean isSend() {
    return send;
  }

  public void setSend(boolean send) {
    this.send = send;
  }

  public boolean isReceive() {
    return receive;
  }

  public void setReceive(boolean receive) {
    this.receive = receive;
  }
}