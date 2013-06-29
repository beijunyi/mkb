package im.grusis.mkb.core.emulator;

import java.util.LinkedHashMap;
import java.util.Map;

import im.grusis.mkb.core.emulator.game.model.basic.*;
import im.grusis.mkb.core.emulator.game.model.response.*;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmulatorMaze {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorMaze.class);

  @Autowired AccountService accountService;
  @Autowired EmulatorCore core;
  @Autowired EmulatorUser user;
  @Autowired ResultProcessor resultProcessor;

  public BattleNormal battle(String username, int mazeId, int layer, int itemIndex) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.getUserInfo(username, false);
    LOG.debug("{} is starting maze battle against maze {}, layer {}, item {}", userInfo, mazeId, layer, itemIndex);
    MkbAccount account = accountService.findAccountByUsername(username);
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("manual", Integer.toString(0));
    params.put("MapStageId", Integer.toString(mazeId));
    params.put("Layer", Integer.toString(layer));
    params.put("ItemIndex", Integer.toString(itemIndex));
    MazeBattleResponse response = core.gameDoAction(username, "maze.php", "Battle", params, MazeBattleResponse.class);
    if(response.badRequest()) {
      if(response.noEnergy()) {
        return null;
      }
      if(response.invalidMazeLayer()) {
        return null;
      }
      throw new UnknownErrorException();
    }
    account.consumeEnergy(MazeInfo.EnergyExpend);
    BattleNormal result = response.getData();
    resultProcessor.processBattleMazeResult(username, result, mazeId);
    return result;
  }

  public MazeInfo info(String username, int mazeId, int level) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.getUserInfo(username, false);
    LOG.debug("{} is retrieving maze level status for maze {} level {}", userInfo, mazeId, level);
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mazeId));
    params.put("Layer", Integer.toString(level));
    MazeInfoResponse response = core.gameDoAction(username, "maze.php", "Info", params, MazeInfoResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    show(username, mazeId, false).setLayer(level);
    LOG.info("{} has successfully retrieved maze level status for maze {} level {}", userInfo, mazeId, level);
    return response.getData();
  }

  public MazeStatus show(String username, int mazeId, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.getUserInfo(username, false);
    LOG.debug("{} is retrieving maze status for maze {}", userInfo, mazeId);
    MazeStatus mazeStatus;
    MkbAccount account = accountService.findAccountByUsername(username);
    if(refresh || (mazeStatus = account.getMazeStatus(mazeId)) == null) {
      Map<String, String> params = new LinkedHashMap<String, String>();
      params.put("MapStageId", Integer.toString(mazeId));
      MazeShowResponse response = core.gameDoAction(username, "maze.php", "Show", params, MazeShowResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      mazeStatus = response.getData();
    }
    account.setMazeStatus(mazeId, mazeStatus);
    accountService.saveAccount(account);
    LOG.info("{} has successfully retrieved maze status for maze {}", userInfo, mazeId);
    return mazeStatus;
  }

  public MazeStatus reset(String username, int mazeId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.getUserInfo(username, false);
    LOG.debug("{} is resetting maze {}", userInfo, mazeId);
    MazeStatus mazeStatus = show(username, mazeId, false);
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mazeId));
    MazeResetResponse response = core.gameDoAction(username, "maze.php", "Reset", params, MazeResetResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    if(!mazeStatus.allowFreeReset()) {
      userInfo.consumeCash(mazeStatus.getResetCash());
    }
    return show(username, mazeId, true);
  }

}
