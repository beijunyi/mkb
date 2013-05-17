package im.grusis.mkb.connection;

import im.grusis.mkb.connection.model.EncryptKeyResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Mothership
 * Date: 13-5-17
 * Time: 上午12:32
 */
public class RegisterHelper {

  private static final Logger Log = LoggerFactory.getLogger(RegisterHelper.class);

  private String token;

  public String getToken(boolean refresh) {
    if(token == null || refresh) {
      token = "js" + (long)Math.floor(Math.random() * System.currentTimeMillis());
    }
    return token;
  }

  public EncryptKeyResponse requestEncryptKey() {
    HttpClient client = new DefaultHttpClient();
    HttpGet get = new HttpGet("http://pp.fantasytoyou.com/pp/userService.do" +
                                "?muhe_id=" + getToken(true) +
                                "&muhe_encode=false" +
                                "&muhe_encrypt=true");
    try {
      HttpResponse response = client.execute(get);
      BasicResponseHandler handler = new BasicResponseHandler();
      String responseString = handler.handleResponse(response);
      Log.info("Received response for encrypt key request {}:\n\t{}", token, responseString.replaceAll("\n", "\n\t"));
      return new EncryptKeyResponse(responseString);
    } catch(Exception e) {
      Log.error("Cannot request encrypt key", e);
    }
    return null;
  }

  public boolean confirmEncryptKey(EncryptKeyResponse encryptKeyResponse, HttpClient client) {
    if(client == null) {
      client = new DefaultHttpClient();
    }
    HttpGet get = new HttpGet("http://pp.fantasytoyou.com/pp/userService.do" +
                                "?muhe_id=" + getToken(false) +
                                "&muhe_encode=false" +
                                "&muhe_encrypt=");
    return false;
  }
}
