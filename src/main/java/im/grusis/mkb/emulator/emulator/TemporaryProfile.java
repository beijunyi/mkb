package im.grusis.mkb.emulator.emulator;

/**
 * User: Mothership
 * Date: 13-6-1
 * Time: 上午12:34
 */
public class TemporaryProfile {
  private String host;
  private String username;
  private String password;
  private long uid;
  private String mac;
  private String key;
  private long time;
  private long lastAccess;

  public TemporaryProfile(String host, String username, String password, long uid, String mac, String key, long time, long lastAccess) {
    this.host = host;
    this.username = username;
    this.password = password;
    this.uid = uid;
    this.mac = mac;
    this.key = key;
    this.time = time;
    this.lastAccess = lastAccess;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public long getLastAccess() {
    return lastAccess;
  }

  public void setLastAccess(long lastAccess) {
    this.lastAccess = lastAccess;
  }

  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }
}
