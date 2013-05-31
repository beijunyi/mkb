package im.grusis.mkb.emulator;

import im.grusis.mkb.emulator.core.MkbCore;
import im.grusis.mkb.emulator.passport.PassportHelper;
import im.grusis.mkb.emulator.passport.model.request.PassportRequest;
import im.grusis.mkb.emulator.passport.model.response.ReturnTemplate;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * User: beij
 * Date: 23/01/13
 * Time: 15:13
 */

@Component
@PropertySource("classpath:/mkb.properties")
public class MkbEmulator {

  @Value("${platform}") private String platform;
  @Value("${language}") private String language;
  @Value("${versionClient}") private String versionClient;
  @Value("${versionBuild}") private String versionBuild;

  private ClientConnectionManager connectionManager = new PoolingClientConnectionManager();
  private DefaultHttpClient shared = new DefaultHttpClient(connectionManager);
//  private Map<String, DefaultHttpClient> httpClients = new HashMap<String, DefaultHttpClient>();

  private DefaultHttpClient getHttpClient(String username) {
//    DefaultHttpClient httpClient = httpClients.get(username);
//    if(httpClient == null) {
//      httpClient = new DefaultHttpClient(connectionManager);
//      httpClients.put(username, httpClient);
//    }
    return shared;
  }

  public MkbCore getMkbCore(String host, String username, long uid, String key, String mac, long time) {
    return new MkbCore(host, username, uid, key, mac, time, getHttpClient(username), platform, language, versionClient, versionBuild);
  }

  public <T extends ReturnTemplate> T passportRequest(PassportRequest<T> passportRequest, Class<T> clazz) {
    PassportHelper helper = new PassportHelper(shared);
    helper.requestEncryptKey();
    helper.proposeCounterKey();
    return helper.sendRequest(passportRequest, clazz);
  }
}
