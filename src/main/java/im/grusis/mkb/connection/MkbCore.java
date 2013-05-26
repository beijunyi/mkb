package im.grusis.mkb.connection;

import java.util.*;

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
  private String uid;
  private String key;
  private long timestamp;



  public MkbCore(String host, String uid, String key, String mac, long timestamp) {

  }

  public String doAction(String service, String action, Map<String, String> params) {
    String url = host + action;
    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    Set<Map.Entry<String, String>> entrySet = params.entrySet();
    for(Map.Entry<String, String> entry: entrySet) {
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
}
