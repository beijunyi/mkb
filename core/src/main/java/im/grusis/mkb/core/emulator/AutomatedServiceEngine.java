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


  @Autowired AccountService accountService;
  @Autowired AssetsService assetsService;
  @Autowired EmulatorUser user;
  @Autowired EmulatorMapStage mapStage;
  @Autowired EmulatorMaze maze;
  @Autowired EmulatorShop shop;
  @Autowired EmulatorWeb web;
  @Autowired EmulatorLogin login;
  @Autowired EmulatorFriend friend;
  @Autowired EmulatorFEnergy fEnergy;
  @Autowired EmulatorLegion legion;
  @Autowired EmulatorArena arena;

  private String getNickname(String username) throws MkbException {
    return user.gameGetUserInfo(username, false).getNickName();
  }

  private int getEnergy(String username) throws MkbException {
    return user.gameGetUserInfo(username, false).getEnergy();
  }

  private String getStageDetailName(String username, int stageDetailId) throws MkbException {
    return mapStage.gameGetMapStageDef(username, stageDetailId).getName();
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
    MazeStatus mazeStatus = maze.gameGetMazeStatus(username, mapStageId, false);
    if(mazeStatus.isMazeClear()) {
      if(!reset) {
        Log.error("Maze {} {} is already cleared", mapStageId, mazeStatus.getName());
        return false;
      }
      if(!mazeStatus.allowFreeReset() && mazeStatus.getResetCash() > resetBudget) {
        Log.error("Cannot reset maze {} {}", mapStageId, mazeStatus.getName());
        return false;
      }
      maze.gameResetMaze(username, mapStageId);
      mazeStatus = maze.gameGetMazeStatus(username, mapStageId, false);
    }
    int layer = mazeStatus.getLayer();
    MazeInfo currentLayer = maze.gameGetMazeLayer(username, mapStageId, layer);
    int maxLayer = currentLayer.getTotalLayer();
    while(true) {
      List<Integer> enemies = currentLayer.getEnemyIndices();
      for(int e : enemies) {
        if(getEnergy(username) < MazeInfo.EnergyExpend) {
          Log.info("Cannot clear maze {} {}. {} {} has insufficient energy", mapStageId, mazeStatus.getName(), username, getNickname(username));
          return false;
        }
        int count = 0;
        while(true) {
          BattleNormal battle = maze.gameMazeBattleAuto(username, mapStageId, layer, e);
          if(battle == null) {
            Log.info("Cannot clear maze {} {}. {} {} has insufficient energy", mapStageId, mazeStatus.getName(), username, getNickname(username));
            return false;
          }
          if(battle.lost()) {
            count++;
            if(count > maxTry) {
              Log.info("Cannot clear maze {} {}. {} {} cannot defeat enemy {} {} on level {}", mapStageId, mazeStatus.getName(), username, getNickname(username), e, battle.getDefendPlayer().getNickName(), currentLayer.getName());
              return false;
            }
          } else {
            Log.info("{} {} has defeated enemy {} {} on level {}", username, getNickname(username), e, battle.getDefendPlayer().getNickName(), layer);
            if(battle.mazeClear()) {
              Log.info("{} {} has cleared maze {} {}", username, getNickname(username), mapStageId, mazeStatus.getName());
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
      currentLayer = maze.gameGetMazeLayer(username, mapStageId, layer);
    }
  }

  public int collectAndSendEnergy(String username, boolean toSameLegion, boolean sendMax) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    final Map<Long, Friend> friendMap = friend.gameGetFriends(username, true);
    int accept = 0;
    List<Long> sameLegion = null;
    String legionName = null;
    if(toSameLegion) {
      sameLegion = new ArrayList<Long>();
      legionName = legion.gameGetUserLegion(username, false).getLegionName();
    }
    for(Friend friend : friendMap.values()) {
      if(toSameLegion && friend.getLegionName().equals(legionName)) {
        sameLegion.add(friend.getUid());
      }
      if(friend.getFEnergySurplus() != 0) {
        if(!fEnergy.gameAcceptEnergy(username, friend.getUid())) {
          break;
        }
        accept++;
      }
    }
    MkbAccount account = accountService.findAccountByUsername(username);
    List<Long> sendList = account.getEnergySenderList();
    boolean max = false;
    for(long fid : sendList) {
      Friend f = friend.gameGetFriend(username, fid);
      if(f == null) {
        account.removeSender(fid);
        accountService.saveAccount(account);
      } else {
        if(f.getFEnergySend() != 0) {
          if(!fEnergy.gameSendEnergy(username, fid)) {
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
        if(friend.gameGetFriend(username, fid).getFEnergySend() != 0) {
          if(!fEnergy.gameSendEnergy(username, fid)) {
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
        if(friend.gameGetFriend(username, fid).getFEnergySend() != 0) {
          if(!fEnergy.gameSendEnergy(username, fid)) {
            break;
          }
        }
      }
    }
    return accept;
  }

  public Map<Integer, UserMapStage> getCounterAttacks(String username) throws ServerNotAvailableException, WrongCredentialException, UnknownErrorException {
    Map<Integer, UserMapStage> stageMap = mapStage.gameGetUserMapStages(username, true);
    Collection<UserMapStage> stages = stageMap.values();
    Map<Integer, UserMapStage> attacked = new TreeMap<Integer, UserMapStage>();
    for(UserMapStage stage : stages) {
      if(stage.getCounterAttackTime() != 0) {
        attacked.put(stage.getMapStageDetailId(), stage);
      }
    }
    return attacked;
  }

  public boolean clearCounterAttacks(String username, int maxTry) throws MkbException {
    if(maxTry < 1) {
      Log.warn("Max try time {} is invalid. A valid value must be at least 1", maxTry);
      maxTry = 1;
    }
    String nickname = getNickname(username);
    Map<Integer, UserMapStage> stageMap = mapStage.gameGetUserMapStages(username, true);
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
        UserMapStage userMapStage = mapStage.gameMapBattleAuto(username, stageDetailId);
        if(userMapStage == null) {
          Log.info("Cannot clear counter attack at {} {}. {} {} has insufficient energy", stageDetailId, stageDetailName, username, nickname);
          return false;
        }
        if(!userMapStage.isCounterAttacked()) {
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
      ArenaCompetitors competitors = arena.gameArenaGetCompetitors(username);
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
    arena.gameArenaFreeFightAuto(username, competitor.getUid(), true);
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
    GameServer gameServer = web.webGetGameServerByDescription(serverDesc);
    if(!web.webReg(username, password, mac, gameServer.getGsId())) {
      return false;
    }
    web.webLogin(username);
    login.gamePassportLogin(username);
    String nickname = null;
    while(nickname == null && nicknameDictionary.hasNext()) {
      String newNickname = nicknameDictionary.next();
      if(user.gameSetNickname(username, sex, inviteCode, newNickname)) {
        nickname = newNickname;
      }
    }
    if(nickname == null) {
      Log.error("Nickname dictionary has no more usable instance");
      return false;
    }
    mapStage.gameMapBattleAuto(username, 1);
    mapStage.gameMapBattleAuto(username, 2);
    user.gameSkipTutorial(username, ItemCode.Tutorial_Fight, ItemCode.Tutorial_Fight_Stages[0]);
    user.gameSkipTutorial(username, ItemCode.Tutorial_Card, ItemCode.Tutorial_Card_Stages[0]);
    user.gameSkipTutorial(username, ItemCode.Tutorial_Card, ItemCode.Tutorial_Card_Stages[1]);
    shop.gamePurchase(username, ItemCode.Ticket);
    return true;
  }


}
