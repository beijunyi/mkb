package im.grusis.mkb.emulator.emulator.passport;

import java.util.ArrayList;
import java.util.List;

import im.grusis.mkb.util.Client;
import im.grusis.mkb.emulator.emulator.passport.model.response.*;
import im.grusis.mkb.emulator.emulator.passport.model.request.PassportRequest;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Mothership
 * Date: 13-5-17
 * Time: 上午12:32
 */
public class PassportHelper {

  private static final Logger Log = LoggerFactory.getLogger(PassportHelper.class);

  public static final int EncryptMode = 2;

  private DefaultHttpClient httpClient;
  private Client client;
  private String token;
  private EncryptKeyResponse encryptKeyResponse;



  public PassportHelper(DefaultHttpClient httpClient) {
    this.httpClient = httpClient;
    client = new Client();
    token = "js" + (long)Math.floor(Math.random() * System.currentTimeMillis());
  }

  public void requestEncryptKey() {
    HttpGet get = new HttpGet("http://pp.fantasytoyou.com/pp/userService.do" +
                                "?muhe_id=" + token +
                                "&muhe_encode=false" +
                                "&muhe_encrypt=true");
    try {
      HttpResponse response = httpClient.execute(get);
      BasicResponseHandler handler = new BasicResponseHandler();
      String responseString = handler.handleResponse(response);
      Log.info("Received response for encrypt key request {}:\n\t{}", token, responseString.replaceAll("\n", "\n\t"));
      encryptKeyResponse = new EncryptKeyResponse(responseString);
    } catch(Exception e) {
      Log.error("Cannot request encrypt key", e);
    }
  }

  public boolean proposeCounterKey() {
    String key = encryptKeyResponse.getKey();
    String counterKey = client.generateKey(key);
    HttpGet get = new HttpGet("http://pp.fantasytoyou.com/pp/userService.do" +
                                "?muhe_id=" + token +
                                "&muhe_encode=false" +
                                "&muhe_encrypt=" + counterKey);
    try {
      HttpResponse response = httpClient.execute(get);
      BasicResponseHandler handler = new BasicResponseHandler();
      String responseString = handler.handleResponse(response);
      Log.info("Received response for counter key proposal {}:\n\t{}", token, responseString.replaceAll("\n", "\n\t"));
      return true;
    } catch(Exception e) {
      Log.error("Cannot propose counter key", e);
    }
    return false;
  }

  public <T extends ReturnTemplate> T sendRequest(PassportRequest<T> passportRequest, Class<T> clazz) {
    HttpPost post = new HttpPost("http://pp.fantasytoyou.com/pp/userService.do?muhe_id=" + token);
    try {
      List<NameValuePair> nvps = new ArrayList<NameValuePair>();
      nvps.add(new BasicNameValuePair("muhe_func", passportRequest.getFunc()));
      nvps.add(new BasicNameValuePair("muhe_args", client.encryptArgs(passportRequest.getArgs(), EncryptMode, 1, 0)));
      nvps.add(new BasicNameValuePair("muhe_encode", "false"));
      nvps.add(new BasicNameValuePair("muhe_encrypt", Integer.toString(EncryptMode)));
      nvps.add(new BasicNameValuePair("muhe_ref", "false"));

      post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

      HttpResponse response = httpClient.execute(post);

      BasicResponseHandler handler = new BasicResponseHandler();
      String responseString = handler.handleResponse(response);
      Log.info("Received response for {} {}:\n\t{}", token, passportRequest.getClass().getSimpleName(), responseString.replaceAll("\n", "\n\t"));

      return ResponseFactory.getResponse(responseString, client.getKey(), clazz);
    } catch(Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
