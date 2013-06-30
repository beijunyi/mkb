package im.grusis.mkb.eco.engines;

import java.util.*;

import im.grusis.mkb.core.emulator.EmulatorMapStage;
import im.grusis.mkb.core.emulator.EmulatorMaze;
import im.grusis.mkb.core.emulator.EmulatorUser;
import im.grusis.mkb.core.emulator.game.model.basic.*;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.service.AssetsService;
import org.apache.commons.lang3.StringUtils;
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

  @Autowired AssetsService assetsService;
  @Autowired EmulatorMapStage mapStage;
  @Autowired EmulatorUser user;
  @Autowired EmulatorMaze maze;

  public Set<UserMapStage> findCounterAttackedMapStages(String username) throws ServerNotAvailableException, WrongCredentialException, UnknownErrorException {
    Map<Integer, UserMapStage> stageMap = mapStage.gameGetUserMapStages(username, true);
    Collection<UserMapStage> stages = stageMap.values();
    Set<UserMapStage> ret = new TreeSet<UserMapStage>();
    for(UserMapStage s : stages) {
      if(s.isCounterAttacked()) {
        ret.add(s);
      }
    }
    return ret;
  }

  public Set<UserMapStage> clearCounterAttackMapStages(String username, List<Integer> stageIds, int maxTry) throws ServerNotAvailableException, WrongCredentialException, UnknownErrorException {
    UserInfo userInfo = user.getUserInfo(username, false);
    LOG.debug("{} is clearing counter attacks at stage {}", userInfo, StringUtils.join(stageIds, ", "));
    Set<UserMapStage> ret = new TreeSet<UserMapStage>();
    for(int sid : stageIds) {
      int count = 0;
      MapStageDef msd = mapStage.getMapStageDetail(username, sid);
      while(true) {
        UserMapStage ums = mapStage.editUserMapStages(username, sid);
        if(ums == null) {
          LOG.warn("{} cannot clear counter attack at {} due to insufficient energy and incorrect energy record", userInfo, ums);
          user.getUserInfo(username, true);
          return ret;
        }
        if(!ums.isCounterAttacked()) {
          LOG.info("{} has cleared the counter attack at {}", userInfo, msd);
          ret.add(ums);
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

  public Map<Integer, MazeStatus> getMazeStatus(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<Integer, UserMapStage> stages = mapStage.gameGetUserMapStages(username, false);
    Map<Integer, Integer> dependency = assetsService.getMazeDependency();
    Map<Integer, MazeStatus> ret = new TreeMap<Integer, MazeStatus>();
    for(Map.Entry<Integer, Integer> m : dependency.entrySet()) {
      if(stages.get(m.getValue()).getFinishedStage() > 0) {
        int mapId = m.getKey();
        ret.put(mapId, maze.show(username, mapId, false));
      }
    }
    return ret;
  }

  public MazeStatus clearMaze(String username, int mazeId, int maxTry) throws ServerNotAvailableException, WrongCredentialException, UnknownErrorException {
    UserInfo userInfo = user.getUserInfo(username, false);
    LOG.debug("{} is clearing maze {}", userInfo, mazeId);
    MazeStatus mazeStatus = maze.show(username, mazeId, false);
    if(mazeStatus.isMazeClear()) {
      return mazeStatus;
    }
    int layer = mazeStatus.getLayer();
    MazeInfo currentLayer = maze.info(username, mazeId, layer);
    int maxLayer = currentLayer.getTotalLayer();
    while(true) {
      List<Integer> enemies = currentLayer.getEnemyIndices();
      for(int e : enemies) {
        if(userInfo.getEnergy() < MazeInfo.EnergyExpend) {
          LOG.info("{} cannot clear maze {} due to insufficient energy", userInfo, mazeId);
          return mazeStatus;
        }
        int count = 0;
        while(true) {
          BattleNormal battle = maze.battle(username, mazeId, layer, e);
          if(battle == null) {
            LOG.info("{} cannot clear maze {} due to insufficient energy", userInfo, mazeId);
            return mazeStatus;
          }
          if(battle.lost()) {
            count++;
            if(count > maxTry) {
              LOG.info("{} cannot clear maze {} due to maximum try times {}", userInfo, mazeId, maxTry);
              return mazeStatus;
            }
          } else {
            LOG.info("{} has defeated enemy {} on level {}", userInfo, battle.getDefendPlayer(), layer);
            if(battle.mazeClear()) {
              LOG.info("{} has cleared maze {}", userInfo, mazeId);
              return mazeStatus;
            }
            break;
          }
        }
      }
      layer++;
      if(layer > maxLayer) {
        layer = 1;
      }
      currentLayer = maze.info(username, mazeId, layer);
    }
  }

  public MazeStatus resetAndClearMaze(String username, int mazeId, int maxTry, int resetBudget) throws ServerNotAvailableException, WrongCredentialException, UnknownErrorException {
    UserInfo userInfo = user.getUserInfo(username, false);
    LOG.debug("{} is starting automatic maze reset and clear process at maze {} with reset budget", userInfo, mazeId, resetBudget);
    if(userInfo.getCash() < resetBudget) {
      resetBudget = userInfo.getCash();
      LOG.debug("{} has a reset budget that is higher than the cash it owns", userInfo, mazeId, resetBudget);
    }
    MazeStatus mazeStatus = maze.show(username, mazeId, false);
    int resetTotal = 0;
    while(true) {
      if(mazeStatus.isMazeClear()) {
        if(mazeStatus.allowFreeReset() || resetTotal + mazeStatus.getResetCash() <= resetBudget) {
          resetTotal += mazeStatus.getResetCash();
          mazeStatus = maze.reset(username, mazeId);
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
