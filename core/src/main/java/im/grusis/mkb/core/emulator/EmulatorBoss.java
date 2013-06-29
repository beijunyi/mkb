package im.grusis.mkb.core.emulator;

import im.grusis.mkb.core.emulator.game.model.basic.BattleNormal;
import im.grusis.mkb.core.emulator.game.model.basic.BossFight;
import im.grusis.mkb.core.emulator.game.model.basic.BossUpdate;
import im.grusis.mkb.core.emulator.game.model.response.*;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmulatorBoss {
  @Autowired EmulatorCore core;

  public BossUpdate gameBossGetBoss(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    BossGetBossResponse response = core.gameDoAction(username, "boss.php", "GetBoss", null, BossGetBossResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public BossFight gameBossFight(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    BossFightResponse response = core.gameDoAction(username, "boss.php", "Fight", null, BossFightResponse.class);
    if(response.badRequest()) {
      if(response.isAlreadyInQueue()) {
        return null;
      }
      if(response.isBossDown()) {
        return null;
      }
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public BattleNormal gameBossGetFightData(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    BossGetFightDataResponse response = core.gameDoAction(username, "boss.php", "GetFightData", null, BossGetFightDataResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public Object gameBossGetRanks(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    BossGetRanksResponse response = core.gameDoAction(username, "boss.php", "GetRanks", null, BossGetRanksResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public Object gameBossBuyTime(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    BossBuyTimeResponse response = core.gameDoAction(username, "boss.php", "BuyTime", null, BossBuyTimeResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public Object gameBossGetStatus(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    BossGetStatusResponse response = core.gameDoAction(username, "boss.php", "GetStatus", null, BossGetStatusResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

}
