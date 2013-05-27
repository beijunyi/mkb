package im.grusis.mkb.connection.core;

import java.util.*;

import im.grusis.mkb.connection.core.model.basic.PassportLogin;
import im.grusis.mkb.connection.core.model.response.GameDataFactory;
import im.grusis.mkb.connection.core.model.response.PassportLoginResponse;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * User: Mothership
 * Date: 13-5-26
 * Time: 下午12:59
 */
public class MkbCore {

  private DefaultHttpClient httpClient;

  private String host;
  private String username;
  private long uid;
  private String key;
  private String mac;
  private long time;

  public MkbCore(String host, String username, long uid, String key, String mac, long time) {
    this.host = host;
    this.username = username;
    this.uid = uid;
    this.key = key;
    this.mac = mac;
    this.time = time;
    httpClient = new DefaultHttpClient();
  }

  public String doAction(String service, String action, Map<String, String> params) {
    String url = host + service + "?do=" + action;
    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    Set<Map.Entry<String, String>> entrySet = params.entrySet();
    for(Map.Entry<String, String> entry : entrySet) {
      nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
    }
    HttpPost post = new HttpPost(url);
    post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
    try {
      HttpResponse response = httpClient.execute(post);
      BasicResponseHandler handler = new BasicResponseHandler();
      return handler.handleResponse(response);
    } catch(Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public PassportLogin doPassportLogin() {
    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("Devicetoken", "");
    paramMap.put("time", Long.toString(time));
    paramMap.put("key", key);
    paramMap.put("Origin", "TTGM");
    paramMap.put("Udid", mac);
    paramMap.put("UserName", username);
    paramMap.put("Password", Long.toString(uid));
    return GameDataFactory.getGameData(doAction("login.php", "PassportLogin", paramMap), PassportLoginResponse.class).getData();
  }
}
