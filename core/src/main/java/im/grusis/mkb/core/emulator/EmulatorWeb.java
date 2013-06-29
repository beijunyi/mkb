package im.grusis.mkb.core.emulator;

import java.util.Map;

import im.grusis.mkb.core.emulator.web.MkbWeb;
import im.grusis.mkb.core.emulator.web.model.basic.GameServer;
import im.grusis.mkb.core.emulator.web.model.basic.LoginInformation;
import im.grusis.mkb.core.emulator.web.model.basic.ServerInformation;
import im.grusis.mkb.core.emulator.web.model.request.*;
import im.grusis.mkb.core.emulator.web.model.response.*;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import im.grusis.mkb.core.service.ArchiveService;
import im.grusis.mkb.core.service.AssetsService;
import im.grusis.mkb.core.util.MacAddressHelper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Mothership
 * Date: 13-6-28
 * Time: 下午11:28
 */
@Component
public class EmulatorWeb {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorWeb.class);

  @Autowired AccountService accountService;
  @Autowired ArchiveService archiveService;
  @Autowired AssetsService assetsService;
  @Autowired EmulatorCore core;

  private DefaultHttpClient shared = new DefaultHttpClient();

  private <T extends ReturnTemplate> T passportRequest(PassportRequest<T> passportRequest, Class<T> clazz) {
    MkbWeb helper = new MkbWeb(shared);
    helper.requestEncryptKey();
    helper.proposeCounterKey();
    return helper.sendRequest(passportRequest, clazz);
  }

  public LoginToken login(String username) throws WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    if(account == null) {
      LOG.error("Cannot login account {}. There is no credential record in the database for this account.", username);
      throw new WrongCredentialException();
    }
    return login(username, account.getPassword(), account.getMac());
  }

  public LoginToken login(String username, String password) throws WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    String mac;
    if(account != null) {
      mac = account.getMac();
    } else {
      mac = MacAddressHelper.getMacAddress();
    }
    return login(username, password, mac);
  }

  public LoginToken login(String username, String password, String mac) throws WrongCredentialException {
    LOG.debug("Starting web login for account {}", username);
    if(username == null) {
      LOG.error("Cannot login account without a username");
      return null;
    }
    if(password == null) {
      LOG.error("Cannot login account {} without a password", username);
      return null;
    }
    if(mac == null) {
      LOG.error("Cannot login account {} without a MAC address", username);
      return null;
    }
    LoginInformationResponse loginInformation = passportRequest(new LoginRequest(username, password, mac), LoginInformationResponse.class);
    LoginInformation ret = loginInformation.getReturnObjs();
    if(!loginInformation.badRequest()) {
      archiveService.addUsername(username);
      LoginToken profile = new LoginToken(ret.getGS_DESC(), ret.getGS_IP(), username, password, ret.getU_ID(), mac, ret.getKey(), ret.getTimestamp());
      core.setLoginToken(username, profile);
      MkbAccount account = accountService.findAccountByUsername(username);
      if(account == null) {
        account = new MkbAccount(username, password, mac);
        archiveService.addUsername(username);
      }
      if(account.getServer() == null) {
        account.setServer(ret.getGS_DESC());
      }
      account.setProfile(profile);
      accountService.saveAccount(account);
      return profile;
    }
    LOG.error("Cannot login account {}. Please check credential.", username);
    throw new WrongCredentialException();
  }

  public boolean register(String username, String password, String mac, long serverId) throws UnknownErrorException {
    if(archiveService.existUsername(username)) {
      LOG.debug("Cannot register account. Username {} is already in use", username);
      return false;
    }
    RegUserResponse resp = passportRequest(new RegUserRequest(username, password, mac, serverId), RegUserResponse.class);
    if(resp.badRequest()) {
      if(resp.duplicateUsername()) {
        LOG.debug("Cannot register account. Username {} is already in use", username);
        archiveService.addUsername(username);
        return false;
      }
      throw new UnknownErrorException();
    }
    MkbAccount account = new MkbAccount(username, password, mac);
    accountService.saveAccount(account);
    archiveService.addUsername(username);
    return true;
  }

  public Map<String, GameServer> getServers(boolean refresh) {
    Map<String, GameServer> servers;
    if(refresh || (servers = assetsService.getGameServerLookup()).isEmpty()) {
      ServerInformation serverInformation = passportRequest(new ServerRequest(), ServerInformationResponse.class).getReturnObjs();
      servers = assetsService.saveAssets(serverInformation.getGAME_SERVER());
    }
    return servers;
  }

  public GameServer webGetGameServerByDescription(String desc) {
    GameServer gameServer = assetsService.findGameServer(desc);
    if(gameServer == null) {
      gameServer = getServers(true).get(desc);
    }
    return gameServer;
  }

}
