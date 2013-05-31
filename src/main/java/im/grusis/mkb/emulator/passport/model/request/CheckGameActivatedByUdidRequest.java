package im.grusis.mkb.emulator.passport.model.request;

import im.grusis.mkb.emulator.passport.model.response.StringResponse;

/**
 * User: Mothership
 * Date: 13-5-25
 * Time: 下午3:02
 */
public class CheckGameActivatedByUdidRequest extends PassportRequest<StringResponse> {

  public CheckGameActivatedByUdidRequest(String username, long serverId, String mac) {
    argMap.put("udid", mac);
    argMap.put("gameName", "CARD-ANDROID-CHS");
    argMap.put("userName", username);
    argMap.put("friendCode", "");
    argMap.put("selGsId", Long.toString(serverId));
  }

  @Override
  public String getFunc() {
    return "checkGameActivatedByUdid";
  }
}
