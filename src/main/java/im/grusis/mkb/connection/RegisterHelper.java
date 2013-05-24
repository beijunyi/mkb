package im.grusis.mkb.connection;

import java.util.ArrayList;
import java.util.List;

import im.grusis.mkb.Client;
import im.grusis.mkb.connection.model.EncryptKeyResponse;
import im.grusis.mkb.connection.model.ServerInformationResponse;
import im.grusis.mkb.connection.model.response.ServerInformation;
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
public class RegisterHelper {

  private static final Logger Log = LoggerFactory.getLogger(RegisterHelper.class);

  public static final int EncryptMode = 2;

  private DefaultHttpClient httpClient;
  private Client client;
  private String token;
  private EncryptKeyResponse encryptKeyResponse;
  private ServerInformationResponse serverInformationResponse;

  public static ServerInformation getServerInformation() {
    RegisterHelper helper = new RegisterHelper();
    helper.requestEncryptKey();
    helper.proposeCounterKey();
    helper.requestServerInformation();
    return helper.getServerInformationResponse().getModel();
  }

  public RegisterHelper() {
    httpClient = new DefaultHttpClient();
    client = new Client();
    token = "js" + (long)Math.floor(Math.random() * System.currentTimeMillis());
  }

  public RegisterHelper(DefaultHttpClient httpClient, Client client, String token, EncryptKeyResponse encryptKeyResponse) {
    this.httpClient = httpClient;
    this.client = client;
    this.token = token;
    this.encryptKeyResponse = encryptKeyResponse;
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

  public void requestServerInformation() {
    HttpPost post = new HttpPost("http://pp.fantasytoyou.com/pp/userService.do?muhe_id=" + token);

    String args = client.encryptArgs("a:1:{i:0;s:57:\"{\"gameName\":\"CARD-ANDROID-CHS\",\"locale\":\"\",\"udid\":\"null\"}\";}", EncryptMode, 1, 0);

    try {
      List<NameValuePair> nvps = new ArrayList<NameValuePair>();
      nvps.add(new BasicNameValuePair("muhe_func", "getLoginGameServers"));
      nvps.add(new BasicNameValuePair("muhe_args", args));
      nvps.add(new BasicNameValuePair("muhe_encode", "false"));
      nvps.add(new BasicNameValuePair("muhe_encrypt", "2"));
      nvps.add(new BasicNameValuePair("muhe_ref", "false"));

      post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

      HttpResponse response = httpClient.execute(post);

      BasicResponseHandler handler = new BasicResponseHandler();
      String responseString = handler.handleResponse(response);
      Log.info("Received response for server information request {}:\n\t{}", token, responseString.replaceAll("\n", "\n\t"));
      serverInformationResponse = new ServerInformationResponse(responseString, client.getKey());
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public EncryptKeyResponse getEncryptKeyResponse() {
    return encryptKeyResponse;
  }

  public ServerInformationResponse getServerInformationResponse() {
    return serverInformationResponse;
  }
}
