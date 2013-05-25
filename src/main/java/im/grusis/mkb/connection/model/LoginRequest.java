package im.grusis.mkb.connection.model;

/**
 * User: Mothership
 * Date: 13-5-25
 * Time: 下午3:02
 */
public class LoginRequest extends PassportRequest {

  public LoginRequest(String username, String password) {
    argMap.put("userName", username);
    argMap.put("userPassword", password);
    argMap.put("gameName", "CARD-ANDROID-CHS");
    argMap.put("udid", "null");
    argMap.put("clientType", "flash");
    argMap.put("releaseChannel", "");
    argMap.put("locale", "");
  }

  @Override
  public String getFunc() {
    return "login";
  }
}
