package im.grusis.mkb.emulator.passport.model.request;

import im.grusis.mkb.emulator.passport.model.response.StringResponse;

/**
 * User: Mothership
 * Date: 13-5-25
 * Time: 下午3:02
 */
public class CheckUserActivedJsonRequest extends PassportRequest<StringResponse> {

  public CheckUserActivedJsonRequest(String username, String password, long serverId, String mac) {
    argMap.put("userName", username);
    argMap.put("email", "");
    argMap.put("userPassword", password);
    argMap.put("gameName", "CARD-ANDROID-CHS");
    argMap.put("udid", mac);
    argMap.put("clientType", "flash");
    argMap.put("releaseChannel", "");
    argMap.put("locale", "");
    argMap.put("friendCode", "");
    argMap.put("selGsId", Long.toString(serverId));
  }

  @Override
  public String getFunc() {
    return "checkUserActivedJson";
  }
}
