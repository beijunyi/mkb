package im.grusis.mkb.core.emulator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import im.grusis.mkb.core.emulator.game.model.basic.*;
import im.grusis.mkb.core.emulator.game.model.response.*;
import im.grusis.mkb.core.emulator.model.GameVersion;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import im.grusis.mkb.core.service.AssetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmulatorMapStage {

  @Autowired GameVersion gameVersion;
  @Autowired AccountService accountService;
  @Autowired AssetsService assetsService;
  @Autowired EmulatorCore core;
  @Autowired ResultProcessor resultProcessor;

  public MapStageDef getMapStageDetail(String username, int stageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MapStageDef mapStageDef = assetsService.findMapStageDetail(stageDetailId);
    if(mapStageDef == null) {
      getMapStageAll(username, true);
      mapStageDef = assetsService.findMapStageDetail(stageDetailId);
      if(mapStageDef == null) {
        throw new UnknownErrorException();
      }
    }
    return mapStageDef;
  }

  public UserMapStage getUserMapStage(String username, int userMapStageDetailId, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<Integer, UserMapStage> stages = gameGetUserMapStages(username, refresh);
    return stages.get(userMapStageDetailId);
  }

  private Level findCurrentLevel(String username, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MapStageDef stage = getMapStageDetail(username, mapStageDetailId);
    UserMapStage userStage = getUserMapStage(username, mapStageDetailId, false);
    List<Level> levels = stage.getLevels();
    int finished = userStage.getFinishedStage();
    if(finished >= levels.size()) {
      finished = levels.size() - 1;
    }
    return levels.get(finished);
  }

  public Map<Integer, UserMapStage> gameGetUserMapStages(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    Map<Integer, UserMapStage> stages;
    if(refresh || (stages = account.getUserMapStages()) == null) {
      MapStageGetUserMapStagesResponse response = core.gameDoAction(username, "mapstage.php", "GetUserMapStages", null, MapStageGetUserMapStagesResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      stages = response.getData();
      account.setUserMapStages(stages);
      accountService.saveAccount(account);
    }

    return stages;
  }

  public UserMapStage editUserMapStages(String username, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageDetailId", Integer.toString(mapStageDetailId));
    params.put("isManual", Integer.toString(0));
    MapStageEditUserMapStagesResponse response = core.gameDoAction(username, "mapstage.php", "EditUserMapStages", params, MapStageEditUserMapStagesResponse.class);
    if(response.badRequest()) {
      if(response.noEnergy()) {
        return null;
      }
      throw new UnknownErrorException();
    }
    MkbAccount account = accountService.findAccountByUsername(username);
    account.consumeEnergy(findCurrentLevel(username, mapStageDetailId).getEnergyExpend());
    BattleMap result = response.getData();
    resultProcessor.processBattleMapResult(username, result, mapStageDetailId);
    return getUserMapStage(username, mapStageDetailId, false);
  }

  public boolean awardClear(String username, int mapStageId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageId", Integer.toString(mapStageId));
    MapStageAwardClearResponse response = core.gameDoAction(username, "mapstage.php", "AwardClear", params, MapStageAwardClearResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public Explore explore(String username, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("MapStageDetailId", Integer.toString(mapStageDetailId));
    MapStageExploreResponse response = core.gameDoAction(username, "mapstage.php", "Explore", params, MapStageExploreResponse.class);
    MkbAccount account = accountService.findAccountByUsername(username);
    account.consumeEnergy(findCurrentLevel(username, mapStageDetailId).getEnergyExplore());
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public Map<Integer, MapDef> getMapStageAll(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<Integer, MapDef> stages;
    if(refresh || (stages = assetsService.getMapStageLookup()).isEmpty()) {
      MapStageGetMapStageAllResponse response = core.gameDoAction(username, "mapstage.php", "GetMapStageALL&stageNum=" + gameVersion.getMapMax(), null, MapStageGetMapStageAllResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      stages = assetsService.saveAssets(response.getData());
    }
    return stages;
  }

  public MapDef getMapStage(String username, int stageId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MapDef mapStage = assetsService.findMapStage(stageId);
    if(mapStage == null) {
      mapStage = getMapStageAll(username, true).get(stageId);
      if(mapStage == null) {
        throw new UnknownErrorException();
      }
    }
    return mapStage;
  }

}
