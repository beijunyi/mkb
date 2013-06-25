package im.grusis.mkb.core.emulator.engines;

import java.util.*;

import com.sun.deploy.util.StringUtils;
import im.grusis.mkb.core.emulator.MkbEmulator;
import im.grusis.mkb.core.emulator.game.model.basic.*;
import im.grusis.mkb.core.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: beij
 * Date: 24/06/13
 * Time: 12:35
 */
@Component
public class MapEngine {

  private static final Logger LOG = LoggerFactory.getLogger(MapEngine.class);

  @Autowired MkbEmulator emulator;

  public Set<UserMapStage> findCounterAttackedMapStages(String username) throws ServerNotAvailableException, WrongCredentialException, UnknownErrorException {
    Map<Integer, UserMapStage> stageMap = emulator.gameGetUserMapStages(username, true);
    Collection<UserMapStage> stages = stageMap.values();
    Set<UserMapStage> ret = new TreeSet<UserMapStage>();
    for(UserMapStage s : stages) {
      if(s.getCounterAttackTime() != 0) {
        ret.add(s);
      }
    }
    return ret;
  }

  public Map<Integer, UserMapStage> clearCounterAttackMapStages(String username, List<Integer> stageIds, int maxTry) throws ServerNotAvailableException, WrongCredentialException, UnknownErrorException {
    UserInfo userInfo = emulator.gameGetUserInfo(username, false);
    LOG.debug("{} is clearing counter attacks at stage {}", userInfo, StringUtils.join(stageIds, ", "));
    Map<Integer, UserMapStage> ret = new TreeMap<Integer, UserMapStage>();
    for(int sid : stageIds) {
      int count = 0;
      MapStageDef msd = emulator.gameGetMapStageDef(username, sid);
      while(true) {
        UserMapStage ums = emulator.gameMapBattleAuto(username, sid);
        if(ums == null) {
          LOG.warn("{} cannot clear counter attack at {} due to insufficient energy and incorrect energy record", userInfo, ums);
          emulator.gameGetUserInfo(username, true);
          return ret;
        }
        if(!ums.isCounterAttacked()) {
          LOG.info("{} has cleared the counter attack at {}", userInfo, msd);
          ret.put(sid, ums);
          break;
        } else {
          count++;
          if(count > maxTry) {
            LOG.info("{} cannot clear counter attack at {} due to maximum try times {}", userInfo, msd, maxTry);
            break;
          }
        }
      }
    }
    LOG.debug("{} has cleared {}/{} counter attacks", userInfo, ret.size(), stageIds.size());
    return ret;
  }

  public MazeStatus clearMaze(String username, int mazeId, int maxTry) throws ServerNotAvailableException, WrongCredentialException, UnknownErrorException {
    UserInfo userInfo = emulator.gameGetUserInfo(username, false);
    LOG.debug("{} is clearing maze {}", userInfo, mazeId);
    MazeStatus maze = emulator.gameGetMazeStatus(username, mazeId, false);
    if(maze.isMazeClear()) {
      return maze;
    }
    int layer = maze.getLayer();
    MazeInfo currentLayer = emulator.gameGetMazeLayer(username, mazeId, layer);
    int maxLayer = currentLayer.getTotalLayer();
    while(true) {
      List<Integer> enemies = currentLayer.getEnemyIndices();
      for(int e : enemies) {
        if(userInfo.getEnergy() < MazeInfo.EnergyExpend) {
          LOG.info("{} cannot clear maze {} due to insufficient energy", userInfo, mazeId);
          return maze;
        }
        int count = 0;
        while(true) {
          BattleNormal battle = emulator.gameMazeBattleAuto(username, mazeId, layer, e);
          if(battle == null) {
            LOG.info("{} cannot clear maze {} due to insufficient energy", userInfo, mazeId);
            return maze;
          }
          if(battle.lost()) {
            count++;
            if(count > maxTry) {
              LOG.info("{} cannot clear maze {} due to maximum try times {}", userInfo, mazeId, maxTry);
              return maze;
            }
          } else {
            LOG.info("{} has defeated enemy {} on level {}", userInfo, battle.getDefendPlayer(), layer);
            if(battle.mazeClear()) {
              LOG.info("{} has cleared maze {}", userInfo, mazeId);
              return maze;
            }
            break;
          }
        }
      }
      layer++;
      if(layer > maxLayer) {
        layer = 1;
      }
      currentLayer = emulator.gameGetMazeLayer(username, mazeId, layer);
    }
  }

  public MazeStatus resetAndClearMaze(String username, int mazeId, int maxTry, int resetBudget) throws ServerNotAvailableException, WrongCredentialException, UnknownErrorException {
    UserInfo userInfo = emulator.gameGetUserInfo(username, false);
    LOG.debug("{} is starting automatic maze reset and clear process at maze {} with reset budget", userInfo, mazeId, resetBudget);
    if(userInfo.getCash() < resetBudget) {
      resetBudget = userInfo.getCash();
      LOG.debug("{} has a reset budget that is higher than the cash it owns", userInfo, mazeId, resetBudget);
    }
    MazeStatus mazeStatus = emulator.gameGetMazeStatus(username, mazeId, false);
    int resetTotal = 0;
    while(true) {
      if(mazeStatus.isMazeClear()) {
        if(mazeStatus.allowFreeReset() || resetTotal + mazeStatus.getResetCash() <= resetBudget) {
          resetTotal += mazeStatus.getResetCash();
          mazeStatus = emulator.gameResetMaze(username, mazeId);
        } else {
          break;
        }
      }
      if(mazeStatus.isMazeClear()) {
        LOG.warn("{} cannot reset maze {} due to incorrect cash record", userInfo, mazeId);
        return mazeStatus;
      }
      mazeStatus = clearMaze(username, mazeId, maxTry);
      if(!mazeStatus.isMazeClear()) {
        break;
      }
    }
    LOG.info("{} has finished automatic maze reset and clear process at maze {}", userInfo, mazeId);
    return mazeStatus;
  }
}
