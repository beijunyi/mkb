package im.grusis.mkb.connection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * User: Mothership
 * Date: 13-5-17
 * Time: 上午12:32
 */
public class RegisterHelper {

  private String token;

  public String getToken() {
    if(token == null) {
      token = "js" + (long) Math.floor(Math.random() * System.currentTimeMillis());
    }
    return token;
  }

  public void firstConnection() {
    HttpClient client = new DefaultHttpClient();
    HttpGet get = new HttpGet("http://pp.fantasytoyou.com/pp/userService.do" +
                                "?muhe_id=" + getToken() +
                                "&muhe_encode=false" +
                                "&muhe_encrypt=true");
    try {
      HttpResponse response = client.execute(get);
      BasicResponseHandler handler = new BasicResponseHandler();
      System.out.println(handler.handleResponse(response));
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
