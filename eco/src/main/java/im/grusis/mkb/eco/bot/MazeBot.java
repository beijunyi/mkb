package im.grusis.mkb.eco.bot;

import java.util.*;

import im.grusis.mkb.core.emulator.EmulatorMapStage;
import im.grusis.mkb.core.emulator.game.model.basic.MazeStatus;
import im.grusis.mkb.core.emulator.game.model.basic.UserMapStage;
import im.grusis.mkb.core.exception.MkbException;
import im.grusis.mkb.core.service.AssetsService;
import im.grusis.mkb.eco.engines.MapEngine;
import im.grusis.mkb.eco.model.MazeSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MazeBot extends MkbBot<Map<Integer, MazeStatus>> {

  private final static Logger LOG = LoggerFactory.getLogger(BossBot.class);

  private String username;
  private MazeSettings mazeSettings;
  private AssetsService assetsService;
  private EmulatorMapStage map;
  private MapEngine mapEngine;

  public MazeBot(String username, MazeSettings mazeSettings, AssetsService assetsService, EmulatorMapStage map, MapEngine mapEngine) {
    this.username = username;
    this.mazeSettings = mazeSettings;
    this.assetsService = assetsService;
    this.map = map;
    this.mapEngine = mapEngine;
  }

  @Override
  protected Map<Integer, MazeStatus> bot() throws MkbException {
    List<Integer> order = mazeSettings.getClearOrder();
    List<Integer> toClear = new ArrayList<Integer>();
    Map<Integer, UserMapStage> userMapStages = map.gameGetUserMapStages(username, false);
    Map<Integer, Integer> mazeConditions = assetsService.getMazeDependency();
    for(int mazeId : order) {
      Integer conditionStage = mazeConditions.get(mazeId);
      if(conditionStage == null) {
        LOG.warn("Missing maze condition information for maze {}", mazeId);
        continue;
      }
      UserMapStage userMapStage = userMapStages.get(conditionStage);
      if(userMapStage == null) continue;
      if(userMapStage.getFinishedStage() <= 0) continue;
      toClear.add(mazeId);
    }
    Map<Integer, MazeStatus> ret = new TreeMap<Integer, MazeStatus>();
    for(int mazeId : toClear) {
      Integer resetMax = mazeSettings.getResetBudgets().get(mazeId);
      if(resetMax == null) resetMax = 0;
      mapEngine.resetAndClearMaze(username, mazeId, mazeSettings.getMaxTry(), resetMax);
    }
    return ret;
  }
}
