package im.grusis.mkb.connection.passport.model.request;

import im.grusis.mkb.connection.passport.model.response.LoginInformationResponse;

/**
 * User: Mothership
 * Date: 13-5-25
 * Time: 下午3:02
 */
public class LoginRequest extends PassportRequest<LoginInformationResponse> {

  public LoginRequest(String username, String password, String mac) {
    argMap.put("userName", username);
    argMap.put("userPassword", password);
    argMap.put("gameName", "CARD-ANDROID-CHS");
    argMap.put("udid", mac);
    argMap.put("clientType", "flash");
    argMap.put("releaseChannel", "");
    argMap.put("locale", "");
  }

  @Override
  public String getFunc() {
    return "login";
  }
}
