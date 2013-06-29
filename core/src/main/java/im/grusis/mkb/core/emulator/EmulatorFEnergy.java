package im.grusis.mkb.core.emulator;

import java.util.LinkedHashMap;
import java.util.Map;

import im.grusis.mkb.core.emulator.game.model.response.FEnergyGetFEnergyResponse;
import im.grusis.mkb.core.emulator.game.model.response.FEnergySendFEnergyResponse;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
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
public class EmulatorFEnergy {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorFEnergy.class);

  @Autowired AccountService accountService;
  @Autowired EmulatorCore core;
  @Autowired EmulatorUser user;
  @Autowired EmulatorFriend friend;

  public boolean gameSendEnergy(String username, long fid) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("Fid", Long.toString(fid));
    FEnergySendFEnergyResponse response = core.gameDoAction(username, "fenergy.php", "SendFEnergy", params, FEnergySendFEnergyResponse.class);
    if(response.badRequest()) {
      if(response.energySendMax()) {
        return false;
      }
      if(response.energyAlreadySent()) {
        return false;
      }
      throw new UnknownErrorException();
    }
    friend.gameGetFriend(username, fid).setFEnergySend(0);
    return true;
  }

  public boolean gameAcceptEnergy(String username, long fid) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("Fid", Long.toString(fid));
    FEnergyGetFEnergyResponse response = core.gameDoAction(username, "fenergy.php", "GetFEnergy", params, FEnergyGetFEnergyResponse.class);
    if(response.badRequest()) {
      if(response.energyGetMax()) {
        return false;
      }
      if(response.energyOverMax()) {
        return false;
      }
      throw new UnknownErrorException();
    }
    friend.gameGetFriend(username, fid).setFEnergySurplus(0);
    MkbAccount account = accountService.findAccountByUsername(username);
    account.acceptEnergyFrom(fid);
    accountService.saveAccount(account);
    return true;
  }


}
