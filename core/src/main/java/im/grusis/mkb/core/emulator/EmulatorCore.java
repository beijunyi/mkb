package im.grusis.mkb.core.emulator;

import java.util.*;

import im.grusis.mkb.core.emulator.game.MkbGame;
import im.grusis.mkb.core.emulator.game.model.response.GameData;
import im.grusis.mkb.core.emulator.game.model.response.GameDataFactory;
import im.grusis.mkb.core.emulator.model.GameVersion;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Mothership
 * Date: 13-6-28
 * Time: 下午11:23
 */
@Component
public class EmulatorCore {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorCore.class);

  @Autowired GameVersion gameVersion;
  @Autowired AccountService accountService;
  @Autowired EmulatorWeb web;
  @Autowired EmulatorLogin login;

  private ClientConnectionManager connectionManager = new PoolingClientConnectionManager();
  private Map<String, DefaultHttpClient> httpClients = new HashMap<String, DefaultHttpClient>();
  private List<DefaultHttpClient> freeClients = new ArrayList<DefaultHttpClient>();
  private Map<String, MkbGame> cores = new HashMap<String, MkbGame>();
  private Map<String, LoginToken> loginTokens = new HashMap<String, LoginToken>();


  private DefaultHttpClient getHttpClient(String username) {
    DefaultHttpClient httpClient = httpClients.get(username);
    if(httpClient == null) {
      if(!freeClients.isEmpty()) {
        httpClient = freeClients.remove(0);
      } else {
        httpClient = new DefaultHttpClient(connectionManager);
      }
      httpClients.put(username, httpClient);
    }
    return httpClient;
  }

  public MkbGame getMkbCore(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbGame core = cores.get(username);
    if(core == null) {
      LoginToken profile = loginTokens.get(username);
      if(profile == null) {
        LOG.info("Cannot create core. {} does not have a temporary profile. Now try to find history profile from database", username);
        MkbAccount account = accountService.findAccountByUsername(username);
        if(account == null) {
          LOG.error("Cannot find any history record for account {}. Please login with username and password at least once", username);
          throw new WrongCredentialException();
        }
        profile = account.getProfile();
        if(profile == null) {
          LOG.error("Cannot find history profile for account {}. Now try to do web login", username);
          profile = web.webLogin(username);
        }
        login.gamePassportLogin(username);
      }
      core = new MkbGame(profile.getHost(), getHttpClient(username), gameVersion.getPlatform(), gameVersion.getLanguage(), gameVersion.getVersionClient(), gameVersion.getVersionBuild());
      cores.put(username, core);
    }
    return core;
  }

  public String gameDoAction(String username, String service, String action, Map<String, String> paramMap) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbGame core = getMkbCore(username);
    String result = core.doAction(service, action, paramMap);
    accountService.findAccountByUsername(username).updateLastAction();
    return result;
  }

  public <T extends GameData> T gameDoAction(String username, String service, String action, Map<String, String> paramMap, Class<T> clazz) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    String responseString = gameDoAction(username, service, action, paramMap);
    T response = GameDataFactory.getGameData(responseString, clazz);
    if(response.badRequest()) {
      if(response.disconnected()) {
        if(action.equals("PassportLogin")) {
          return response;
        }
        LOG.debug("Previous session is no longer valid. Now try passport login", username);
        login.gamePassportLogin(username);
        return gameDoAction(username, service, action, paramMap, clazz);
      }
      LOG.error("*** ERROR *** {}", responseString);
    }
    return response;
  }

  public void setLoginToken(String username, LoginToken loginToken) {
    loginTokens.put(username, loginToken);
  }

  public LoginToken getLoginToken(String username) {
    return loginTokens.get(username);
  }
}
