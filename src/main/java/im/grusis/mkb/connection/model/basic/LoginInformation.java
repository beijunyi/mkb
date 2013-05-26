package im.grusis.mkb.connection.model.basic;

/**
 * User: Mothership
 * Date: 13-5-24
 * Time: 下午11:14
 */
public class LoginInformation {
  String GS_NAME;
  String GS_IP;
  String friendCode;
  String GS_PORT;
  long timestamp;
  int GS_CHAT_PORT;
  String source;
  String userName;
  String GS_DESC;
  long U_ID;
  String key;
  int G_TYPE;
  String GS_CHAT_IP;

  public String getGS_NAME() {
    return GS_NAME;
  }

  public void setGS_NAME(String GS_NAME) {
    this.GS_NAME = GS_NAME;
  }

  public String getGS_IP() {
    return GS_IP;
  }

  public void setGS_IP(String GS_IP) {
    this.GS_IP = GS_IP;
  }

  public String getFriendCode() {
    return friendCode;
  }

  public void setFriendCode(String friendCode) {
    this.friendCode = friendCode;
  }

  public String getGS_PORT() {
    return GS_PORT;
  }

  public void setGS_PORT(String GS_PORT) {
    this.GS_PORT = GS_PORT;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public int getGS_CHAT_PORT() {
    return GS_CHAT_PORT;
  }

  public void setGS_CHAT_PORT(int GS_CHAT_PORT) {
    this.GS_CHAT_PORT = GS_CHAT_PORT;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getGS_DESC() {
    return GS_DESC;
  }

  public void setGS_DESC(String GS_DESC) {
    this.GS_DESC = GS_DESC;
  }

  public long getU_ID() {
    return U_ID;
  }

  public void setU_ID(long u_ID) {
    U_ID = u_ID;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public int getG_TYPE() {
    return G_TYPE;
  }

  public void setG_TYPE(int g_TYPE) {
    G_TYPE = g_TYPE;
  }

  public String getGS_CHAT_IP() {
    return GS_CHAT_IP;
  }

  public void setGS_CHAT_IP(String GS_CHAT_IP) {
    this.GS_CHAT_IP = GS_CHAT_IP;
  }
}
