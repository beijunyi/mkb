package im.grusis.mkb.core.emulator;

import java.util.*;

import im.grusis.mkb.core.emulator.game.model.basic.*;
import im.grusis.mkb.core.emulator.web.model.basic.GameServer;
import im.grusis.mkb.core.exception.*;
import im.grusis.mkb.core.repository.model.BattleRecord;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import im.grusis.mkb.core.service.AssetsService;
import im.grusis.mkb.core.util.MacAddressHelper;
import im.grusis.mkb.core.util.MkbDictionary;
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
  @Autowired AssetsService assetsService;

  private String getNickname(String username) throws MkbException {
    return emulator.gameGetUserInfo(username, false).getNickName();
  }

  private int getEnergy(String username) throws MkbException {
    return emulator.gameGetUserInfo(username, false).getEnergy();
  }

  private String getStageDetailName(String username, int stageDetailId) throws MkbException {
    return emulator.gameGetMapStageDetail(username, stageDetailId).getName();
  }

  public Map<Integer, MazeShow> getMazeStatus(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<Integer, UserMapStage> stages = emulator.gameGetUserMapStages(username, false);
    Map<Integer, Integer> dependency = assetsService.getMazeDependency();
    Map<Integer, MazeShow> ret = new TreeMap<Integer, MazeShow>();
    for(Map.Entry<Integer, Integer> maze : dependency.entrySet()) {
      if(stages.get(maze.getValue()).getFinishedStage() > 0) {
        int mapId = maze.getKey();
        ret.put(mapId, emulator.gameGetMaze(username, mapId));
      }
    }
    return ret;
  }

  public boolean clearMaze(String username, int mapStageId, int maxTry, boolean reset, int resetBudget) throws MkbException {
    if(maxTry < 1) {
      Log.warn("Max try time {} is invalid. A valid value must be at least 1", maxTry);
      maxTry = 1;
    }
    if(resetBudget < 0) {
      Log.warn("Reset budget {} is invalid. A valid value must be at least 0", resetBudget);
      resetBudget = 0;
    }
    MazeShow maze = emulator.gameGetMaze(username, mapStageId);
    if(maze.clear()) {
      if(!reset) {
        Log.error("Maze {} {} is already cleared", mapStageId, maze.getName());
        return false;
      }
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
        if(getEnergy(username) < MazeInfo.EnergyExpend) {
          Log.info("Cannot clear maze {} {}. {} {} has insufficient energy", mapStageId, maze.getName(), username, getNickname(username));
          return false;
        }
        int count = 0;
        while(true) {
          BattleNormal battle = emulator.gameMazeBattleAuto(username, mapStageId, layer, e);
          if(battle == null) {
            Log.info("Cannot clear maze {} {}. {} {} has insufficient energy", mapStageId, maze.getName(), username, getNickname(username));
            return false;
          }
          if(battle.lost()) {
            count++;
            if(count > maxTry) {
              Log.info("Cannot clear maze {} {}. {} {} cannot defeat enemy {} {} on level {}", mapStageId, maze.getName(), username, getNickname(username), e, battle.getDefendPlayer().getNickName(), currentLayer.getName());
              return false;
            }
          } else {
            Log.info("{} {} has defeated enemy {} {} on level {}", username, getNickname(username), e, battle.getDefendPlayer().getNickName(), layer);
            if(battle.mazeClear()) {
              Log.info("{} {} has cleared maze {} {}", username, getNickname(username), mapStageId, maze.getName());
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
      Friend friend = emulator.gameGetFriend(username, fid);
      if(friend == null) {
        account.removeSender(fid);
        accountService.saveAccount(account);
      } else {
        if(friend.getFEnergySend() != 0) {
          if(!emulator.gameSendEnergy(username, fid)) {
            max = true;
            break;
          }
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

  public boolean clearCounterAttacks(String username, int maxTry) throws MkbException {
    if(maxTry < 1) {
      Log.warn("Max try time {} is invalid. A valid value must be at least 1", maxTry);
      maxTry = 1;
    }
    String nickname = getNickname(username);
    Map<Integer, UserMapStage> stageMap = emulator.gameGetUserMapStages(username, true);
    Collection<UserMapStage> stages = stageMap.values();
    List<Integer> attacked = new ArrayList<Integer>();
    for(UserMapStage stage : stages) {
      if(stage.getCounterAttackTime() != 0) {
        attacked.add(stage.getMapStageDetailId());
      }
    }
    for(int stageDetailId : attacked) {
      int count = 0;
      while(true) {
        String stageDetailName = getStageDetailName(username, stageDetailId);
        BattleMap battle = emulator.gameMapBattleAuto(username, stageDetailId);
        if(battle == null) {
          Log.info("Cannot clear counter attack at {} {}. {} {} has insufficient energy", stageDetailId, stageDetailName, username, nickname);
          return false;
        }
        if(battle.win()) {
          Log.info("{} {} has cleared the counter attack at {} {}", username, nickname, stageDetailId, stageDetailName);
          break;
        } else {
          count++;
          if(count > maxTry) {
            Log.info("Cannot clear counter attack {} {}. {} {} has been defeated {} times", stageDetailId, stageDetailName, username, nickname, maxTry);
            return false;
          }
        }
      }
    }
    Log.info("{} {} has cleared {} counter attacks", username, nickname, attacked.size());
    return true;
  }

  public boolean findChipFreeFight(String username, int maxRefresh, final Map<Long, BattleRecord> battleRecords) throws MkbException {
    int count = 0;
    List<ArenaCompetitor> chipCompetitors = new ArrayList<ArenaCompetitor>();
    while(chipCompetitors.isEmpty() && count < maxRefresh) {
      ArenaCompetitors competitors = emulator.gameArenaGetCompetitors(username);
      if(competitors.getCountdown() > 0) {
        return false;
      }
      for(ArenaCompetitor competitor : competitors.getCompetitors()) {
        if(competitor.isChip()) {
          chipCompetitors.add(competitor);
        }
      }
    }
    if(chipCompetitors.isEmpty()) {
      return false;
    }
    Collections.sort(chipCompetitors, new Comparator<ArenaCompetitor>() {
      @Override
      public int compare(ArenaCompetitor o1, ArenaCompetitor o2) {
        if(battleRecords != null) {
          BattleRecord r1 = battleRecords.get(o1.getUid());
          BattleRecord r2 = battleRecords.get(o2.getUid());
          double p1 = r1 == null ? 0.5d : r1.getPercent();
          double p2 = r2 == null ? 0.5d : r2.getPercent();
          if(p1 == p2) {
            return Integer.compare(o2.getRank(), o1.getRank());
          } else {
            return Double.compare(p2, p1);
          }
        } else {
          return Integer.compare(o2.getRank(), o1.getRank());
        }
      }
    });
    ArenaCompetitor competitor = chipCompetitors.get(0);
    emulator.gameArenaFreeFightAuto(username, competitor.getUid(), true);
    return true;
  }

  public boolean registerNewAccount(String username, String password, int sex, MkbDictionary nicknameDictionary, String serverDesc, String inviteCode) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    String mac = null;
    while(mac == null) {
      String newMac = MacAddressHelper.getMacAddress();
      if(accountService.findAccountByMac(mac) == null) {
        mac = newMac;
      }
    }
    GameServer gameServer = emulator.webGetGameServerByDescription(serverDesc);
    if(!emulator.webReg(username, password, mac, gameServer.getGsId())) {
      return false;
    }
    emulator.webLogin(username);
    emulator.gamePassportLogin(username);
    String nickname = null;
    while(nickname == null && nicknameDictionary.hasNext()) {
      String newNickname = nicknameDictionary.next();
      if(emulator.gameSetNickname(username, sex, inviteCode, newNickname)) {
        nickname = newNickname;
      }
    }
    if(nickname == null) {
      Log.error("Nickname dictionary has no more usable instance");
      return false;
    }
    emulator.gameMapBattleAuto(username, 1);
    emulator.gameMapBattleAuto(username, 2);
    emulator.gameSkipTutorial(username, ItemCode.Tutorial_Fight, ItemCode.Tutorial_Fight_Stages[0]);
    emulator.gameSkipTutorial(username, ItemCode.Tutorial_Card, ItemCode.Tutorial_Card_Stages[0]);
    emulator.gameSkipTutorial(username, ItemCode.Tutorial_Card, ItemCode.Tutorial_Card_Stages[1]);
    emulator.gamePurchase(username, ItemCode.Ticket);
    return true;
  }


}
