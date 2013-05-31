package im.grusis.mkb.emulator.emulator.core.model.basic;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 上午1:07
 */
public class PassportLogin {
  private int isSetNick;
  private boolean invite;
  private boolean gscode;
  private boolean minor;
  private String cdnurl;
  private String ip;
  private int ipport;

  public int getSetNick() {
    return isSetNick;
  }

  public void setSetNick(int setNick) {
    isSetNick = setNick;
  }

  public boolean isInvite() {
    return invite;
  }

  public void setInvite(boolean invite) {
    this.invite = invite;
  }

  public boolean isGscode() {
    return gscode;
  }

  public void setGscode(boolean gscode) {
    this.gscode = gscode;
  }

  public boolean isMinor() {
    return minor;
  }

  public void setMinor(boolean minor) {
    this.minor = minor;
  }

  public String getCdnurl() {
    return cdnurl;
  }

  public void setCdnurl(String cdnurl) {
    this.cdnurl = cdnurl;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public int getIpport() {
    return ipport;
  }

  public void setIpport(int ipport) {
    this.ipport = ipport;
  }
}
