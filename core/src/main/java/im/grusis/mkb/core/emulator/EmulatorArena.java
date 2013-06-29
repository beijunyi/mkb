package im.grusis.mkb.core.emulator;

import java.util.LinkedHashMap;
import java.util.Map;

import im.grusis.mkb.core.emulator.game.model.basic.*;
import im.grusis.mkb.core.emulator.game.model.response.ArenaFreeFightResponse;
import im.grusis.mkb.core.emulator.game.model.response.ArenaGetCompetitorsResponse;
import im.grusis.mkb.core.emulator.game.model.response.ArenaGetThievesResponse;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import im.grusis.mkb.core.service.ArchiveService;
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
public class EmulatorArena {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorArena.class);

  @Autowired AccountService accountService;
  @Autowired ArchiveService archiveService;
  @Autowired EmulatorCore core;
  @Autowired EmulatorUser user;

  public ArenaCompetitors gameArenaGetCompetitors(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.gameGetUserInfo(username, false);
    LOG.debug("{} is retrieving arena competitor list", userInfo);
    ArenaGetCompetitorsResponse response = core.gameDoAction(username, "arena.php", "GetCompetitors", null, ArenaGetCompetitorsResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    ArenaCompetitors competitors = response.getData();
    LOG.info("{} has successfully retrieved arena competitor list", userInfo);
    return competitors;
  }

  public BattleNormal gameArenaFreeFightAuto(String username, long competitor, boolean forChip) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.gameGetUserInfo(username, false);
    LOG.debug("{} is starting arena free fight against {}", userInfo, competitor);
    Map<String, String> params = new LinkedHashMap<String, String>();
    if(!forChip) {
      params.put("NoChip", Integer.toString(1));
    }
    params.put("isManual", Integer.toString(0));
    params.put("competitor", Long.toString(competitor));
    ArenaFreeFightResponse response = core.gameDoAction(username, "arena.php", "FreeFight", params, ArenaFreeFightResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    BattleNormal battle = response.getData();
    MkbAccount account = accountService.findAccountByUsername(username);
    if(battle.win()) {
      LOG.info("{} has won the arena free fight against {}", userInfo, battle.getDefendPlayer());
      account.battle(competitor, true);
    } else {
      LOG.info("{} has lost the arena free fight against {}", userInfo, battle.getDefendPlayer());
      account.battle(competitor, false);
    }
    accountService.saveAccount(account);
    return battle;
  }

  public BattleNormal gameArenaRankFight(String username, int rank) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.gameGetUserInfo(username, false);
    LOG.debug("{} is starting arena rank fight against competitor ranked {}", userInfo);
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("CompetitorRank", Integer.toString(rank));
    ArenaFreeFightResponse response = core.gameDoAction(username, "arena.php", "RankFight", params, ArenaFreeFightResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    BattleNormal battle = response.getData();
    MkbAccount account = accountService.findAccountByUsername(username);
    Player defendPlayer = battle.getDefendPlayer();
    long uid = defendPlayer.getUid();
    if(battle.win()) {
      LOG.info("{} has won the arena rank fight against {}", userInfo, defendPlayer);
      account.battle(uid, true);
    } else {
      LOG.info("{} has lost the arena rank fight against {}", userInfo, defendPlayer);
      account.battle(uid, false);
    }
    accountService.saveAccount(account);
    return battle;
  }

  public Map<Long, ThievesInfo> gameGetThieves(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    ArenaGetThievesResponse response = core.gameDoAction(username, "arena.php", "GetThieves", null, ArenaGetThievesResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    Thieves thieves = response.getData();
    Map<Long, ThievesInfo> thiefMap = new LinkedHashMap<Long, ThievesInfo>();
    for(ThievesInfo thief : thieves.getThieves()) {
      thiefMap.put(thief.getUserThievesId(), thief);
    }
    return thiefMap;
  }


}
