package im.grusis.mkb.connection.model.request;

import im.grusis.mkb.connection.model.response.ServerInformationResponse;

/**
 * User: Mothership
 * Date: 13-5-25
 * Time: 下午3:02
 */
public class ServerRequest extends PassportRequest<ServerInformationResponse> {

  public ServerRequest() {
    argMap.put("gameName", "CARD-ANDROID-CHS");
    argMap.put("locale", "");
    argMap.put("udid", "null");
  }

  @Override
  public String getFunc() {
    return "getLoginGameServers";
  }
}
