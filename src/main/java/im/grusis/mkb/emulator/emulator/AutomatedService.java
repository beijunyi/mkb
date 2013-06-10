package im.grusis.mkb.emulator.emulator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import im.grusis.mkb.emulator.emulator.core.model.basic.*;
import im.grusis.mkb.exception.ServerNotAvailableException;
import im.grusis.mkb.exception.UnknownErrorException;
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
public class AutomatedService {
  private static final Logger Log = LoggerFactory.getLogger(AutomatedService.class);


  @Autowired MkbEmulator emulator;
  @Autowired AccountService accountService;

  private Map<Integer, Integer> mazeDependency;

  private Map<Integer, Integer> getMazeDependency(String username) throws ServerNotAvailableException, UnknownErrorException {
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

  public boolean clearMaze(String username, int mapStageId, int maxTry, int resetBudget) throws ServerNotAvailableException, UnknownErrorException {
    MkbAccount account = accountService.findAccountByUsername(username);
    MazeShow maze = emulator.gameGetMaze(username, mapStageId);
    if(maze.clear()) {
      if(!maze.freeReset() && maze.getResetCash() > resetBudget) {
        Log.error("Cannot reset maze {} {}", mapStageId, maze.getName());
        return false;
      }
      emulator.gameResetMaze(username, mapStageId);
    }

    return false;
  }

}
