package im.grusis.mkb.emulator.emulator;

import java.util.*;

import im.grusis.mkb.emulator.emulator.core.model.basic.*;
import im.grusis.mkb.exception.ServerNotAvailableException;
import im.grusis.mkb.exception.UnknownErrorException;
import im.grusis.mkb.exception.WrongCredentialException;
import im.grusis.mkb.internal.MkbAccount;
import im.grusis.mkb.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Mothership
 * Date: 13-6-9
 * Time: 上午1:57
 */
@Component
public class AutomatedServiceEngine {
  private static final Logger Log = LoggerFactory.getLogger(AutomatedServiceEngine.class);


  @Autowired MkbEmulator emulator;
  @Autowired AccountService accountService;

  private Map<Integer, Integer> mazeDependency;

  private Map<Integer, Integer> getMazeDependency(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    if(mazeDependency == null) {
      mazeDependency = new LinkedHashMap<Integer, Integer>();
      MapStageAll stages = emulator.gameGetMapStages(username, false);
      for(MapStage stage : stages) {
        List<MapStageDetail> details = stage.getMapStageDetails();
        boolean hasMaze = false;
        int boss = -1;
        for(MapStageDetail detail : details) {
          int type = detail.getType();
          if(type == MapStageDetail.MazeLevel) {
            hasMaze = true;
          } else if(type == MapStageDetail.BossLevel) {
            boss = detail.getMapStageDetailId();
          }
          if(hasMaze && boss != -1) {
            break;
          }
        }
        if(hasMaze) {
          if(boss == -1) {
            Log.error("Cannot find boss level for map stage {} {}", stage.getMapStageId(), stage.getName());
            throw new UnknownErrorException();
          }
          mazeDependency.put(stage.getMapStageId(), boss);
        }
      }
    }
    return mazeDependency;
  }

  public boolean clearMaze(String username, int mapStageId, int maxTry, int resetBudget) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    if(maxTry < 1) {
      Log.warn("Max try time {} is invalid. A valid value must be at least 1", maxTry);
      maxTry = 1;
    }
    if(resetBudget < 0) {
      Log.warn("Reset budget {} is invalid. A valid value must be at least 0", resetBudget);
      maxTry = 1;
    }
    MkbAccount account = accountService.findAccountByUsername(username);
    MazeShow maze = emulator.gameGetMaze(username, mapStageId);
    if(maze.clear()) {
      if(!maze.freeReset() && maze.getResetCash() > resetBudget) {
        Log.error("Cannot reset maze {} {}", mapStageId, maze.getName());
        return false;
      }
      emulator.gameResetMaze(username, mapStageId);
      maze = emulator.gameGetMaze(username, mapStageId);
    }
    int layer = maze.getLayer();
    MazeInfo currentLayer = emulator.gameGetMazeLayer(username, mapStageId, layer);
    int maxLayer = currentLayer.getTotalLayer();
    while(true) {
      List<Integer> enemies = currentLayer.getEnemyIndices();
      for(int e : enemies) {
        if(account.getEnergy() < MazeInfo.EnergyExpend) {
          Log.info("Cannot clear maze {} {}. {} {} has insufficient energy", mapStageId, maze.getName(), username, account.getNickname());
          return false;
        }
        int count = 0;
        while(true) {
          BattleMaze battle = emulator.gameMazeBattleAuto(username, mapStageId, layer, e);
          if(battle == null) {
            Log.info("Cannot clear maze {} {}. {} {} has insufficient energy", mapStageId, maze.getName(), username, account.getNickname());
            return false;
          }
          if(battle.lost()) {
            count++;
            if(count > maxTry) {
              Log.info("Cannot clear maze {} {}. {} {} cannot defeat enemy {} {} on level {}", mapStageId, maze.getName(), username, account.getNickname(), e, battle.getDefendPlayer().getNickName(), currentLayer.getName());
              return false;
            }
          } else {
            Log.info("{} {} has defeated enemy {} {} on level {}", username, account.getNickname(), e, battle.getDefendPlayer().getNickName(), layer);
            if(battle.mazeClear()) {
              Log.info("{} {} has cleared maze {} {}", username, account.getNickname(), mapStageId, maze.getName());
              return true;
            }
            break;
          }
        }
      }
      layer++;
      if(layer > maxLayer) {
        layer = 1;
      }
      currentLayer = emulator.gameGetMazeLayer(username, mapStageId, layer);
    }
  }

  public int collectAndSendEnergy(String username, boolean toSameLegion, boolean sendMax) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    final Map<Long, Friend> friendMap = emulator.gameGetFriends(username, true);
    int accept = 0;
    List<Long> sameLegion = null;
    String legionName = null;
    if(toSameLegion) {
      sameLegion = new ArrayList<Long>();
      legionName = emulator.gameGetMyLegion(username, false).getName();
    }
    for(Friend friend : friendMap.values()) {
      if(toSameLegion && friend.getLegionName().equals(legionName)) {
        sameLegion.add(friend.getUid());
      }
      if(friend.getFEnergySurplus() != 0) {
        if(!emulator.gameAcceptEnergy(username, friend.getUid())) {
          break;
        }
        accept++;
      }
    }
    MkbAccount account = accountService.findAccountByUsername(username);
    List<Long> sendList = account.getEnergySenderList();
    boolean max = false;
    for(long fid : sendList) {
      if(emulator.gameGetFriend(username, fid).getFEnergySend() != 0) {
        if(!emulator.gameSendEnergy(username, fid)) {
          max = true;
          break;
        }
      }
    }
    if(max) {
      return accept;
    }
    if(toSameLegion) {
      for(long fid : sameLegion) {
        if(emulator.gameGetFriend(username, fid).getFEnergySend() != 0) {
          if(!emulator.gameSendEnergy(username, fid)) {
            max = true;
            break;
          }
        }
      }
    }
    if(!max && sendMax) {
      List<Long> rankList = new ArrayList<Long>(friendMap.keySet());
      Collections.sort(rankList, new Comparator<Long>() {
        @Override
        public int compare(Long o1, Long o2) {
          return Integer.compare(friendMap.get(o1).getRank(), friendMap.get(o2).getRank());
        }
      });
      for(long fid : rankList) {
        if(emulator.gameGetFriend(username, fid).getFEnergySend() != 0) {
          if(!emulator.gameSendEnergy(username, fid)) {
            break;
          }
        }
      }
    }
    return accept;
  }

  public boolean clearCounterAttacks(String username, int maxTry) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    if(maxTry < 1) {
      Log.warn("Max try time {} is invalid. A valid value must be at least 1", maxTry);
      maxTry = 1;
    }
    UserMapStages stageMap = emulator.gameGetUserMapStages(username, true);
    Collection<UserMapStage> stages = stageMap.values();
    List<Integer> attacked = new ArrayList<Integer>();
    for(UserMapStage stage : stages) {
      if(stage.getCounterAttackTime() != 0) {
        attacked.add(stage.getMapStageDetailId());
      }
    }
    MkbAccount account = accountService.findAccountByUsername(username);
    for(int stageId : attacked) {
      int count = 0;
      while(true) {
        BattleMap battle = emulator.gameMapBattleAuto(username, stageId);
        if(battle == null) {
          Log.info("Cannot clear counter attack {} {} difficulty {} . {} {} has insufficient energy", stageId, emulator.gameGetMapStageDetail(username, stageId).getName(), stageMap.get(stageId).getFinishedStage(), username, account.getNickname());
          return false;
        }
        if(battle.win()) {
          break;
        } else {
          count++;
          if(count > maxTry) {
            Log.info("Cannot clear counter attack {} {} difficulty {} . {} {} has been defeated {} times", stageId, emulator.gameGetMapStageDetail(username, stageId).getName(), stageMap.get(stageId).getFinishedStage(), username, account.getNickname(), maxTry);
            return false;
          }
        }
      }
    }
    Log.info("{} {} has cleared {} counter attacks", username, account.getNickname(), attacked.size());
    return true;
  }

}
