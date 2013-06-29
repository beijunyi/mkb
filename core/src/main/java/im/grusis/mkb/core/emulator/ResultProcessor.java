package im.grusis.mkb.core.emulator;

import im.grusis.mkb.core.emulator.game.model.basic.*;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Mothership
 * Date: 13-6-29
 * Time: 上午12:19
 */
@Component
public class ResultProcessor {

  private static final Logger LOG = LoggerFactory.getLogger(ResultProcessor.class);


  @Autowired AccountService accountService;
  @Autowired EmulatorUser user;
  @Autowired EmulatorCard card;
  @Autowired EmulatorChip chip;
  @Autowired EmulatorMaze maze;
  @Autowired EmulatorMapStage mapStage;

  public void processGoodsPurchaseResult(String username, String result) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.gameGetUserInfo(username, false);
    LOG.debug("Processing purchase result {} for {}", result, userInfo);
    MkbAccount account = accountService.findAccountByUsername(username);
    String[] cardIdArray = result.split("_");
    StringBuilder sb = new StringBuilder();
    for(String id : cardIdArray) {
      if(sb.length() > 0) {
        sb.append(", ");
      }
      int cardId = Integer.parseInt(id);
      account.addNewCard(cardId);
      sb.append(card.gameGetCardDetail(username, cardId));
    }
    LOG.info("{} has obtained {}", userInfo, sb.toString());
  }

  public void processBattleResult(String username, BattleNormal result) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.gameGetUserInfo(username, false);
    BattleNormalExtData ext = result.getExtData();
    if(ext != null) {
      BattleNormalExtData.User user = ext.getUser();
      userInfo.setLevel(user.getLevel());

      BattleNormalExtData.Award award = ext.getAward();
      int coins = award.getCoins();
      int exp = award.getExp();
      userInfo.addCoins(coins);
      userInfo.addExp(exp);
      LOG.info("{} has obtained {} coins and {} exp as maze battle reward", userInfo, coins, exp);
      int cardId = award.getCardId();
      int chipId = award.getChip();
      if(cardId > 0 || chipId > 0) {
        MkbAccount account = accountService.findAccountByUsername(username);
        if(cardId > 0) {
          account.addNewCard(award.getCardId());
          LOG.info("{} has obtained {} as maze battle reward", userInfo, card.gameGetCardDetail(username, cardId));
        }
        if(chipId > 0) {
          ChipPuzzle chipPuzzle = chip.gameGetUserChip(username, false);
          if(chipPuzzle.addChip(chipId)) {
            userInfo.addTicket();
            LOG.info("{} has obtained chip fragment {} finishing chip collection and gain 1 ticket", userInfo, chipId);
          } else {
            LOG.info("{} has obtained chip fragment {}", userInfo, chipId);
          }
        }
      }
    }
  }

  public void processBonus(String username, String... bonuses) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.gameGetUserInfo(username, false);
    LOG.debug("Processing bonus {} for {}", bonuses, userInfo);
    MkbAccount account = accountService.findAccountByUsername(username);
    for(String bonus : bonuses) {
      String[] pair = bonus.split("_");
      String key = pair[0].toLowerCase();
      int value = Integer.parseInt(pair[1]);
      if(key.contains("exp")) {
        userInfo.addExp(value);
        LOG.info("{} has obtained {} exp", userInfo, value);
      } else if(key.contains("coin")) {
        userInfo.addCoins(value);
        LOG.info("{} has obtained {} coins", userInfo, value);
      } else if(key.contains("card")) {
        account.addNewCard(value);
        LOG.info("{} has obtained {}", userInfo, card.gameGetCardDetail(username, value));
      } else if(key.contains("chip")) {
        ChipPuzzle chipPuzzle = chip.gameGetUserChip(username, false);
        if(chipPuzzle.addChip(value)) {
          userInfo.addTicket();
          LOG.info("{} has obtained chip fragment {} finishing chip collection and gain 1 ticket", userInfo, value);
        } else {
          LOG.info("{} has obtained chip fragment {}", userInfo, value);
        }
      } else {
        LOG.warn("{} has obtained *** UNKNOWN REWARD *** {}", userInfo, bonus);
      }
    }
  }

  public UserMapStage processBattleMapResult(String username, BattleMap result, int mapStageDetailId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.gameGetUserInfo(username, false);
    MapStageDef mapStageDef = mapStage.gameGetMapStageDef(username, mapStageDetailId);
    UserMapStage userMapStage = mapStage.gameGetUserMapStage(username, mapStageDetailId, false);
    if(result.win()) {
      userMapStage.clearCounterAttack();
    }
    BattleMapExtData ext = result.getExtData();
    if(ext != null) {
      userInfo.setLevel(ext.getUserLevel());
      if(ext.getStarUp() > 0) {
        LOG.info("{} has conquered {} difficulty {}", userInfo, mapStageDef, userMapStage.starUp());
      }
      String[] bonuses = ext.getBonus();
      if(bonuses != null) {
        processBonus(username, bonuses);
      }
      String[] firstBonuses = ext.getFirstBonusWin();
      if(firstBonuses != null) {
        processBonus(username, firstBonuses);
      }
    }
    return userMapStage;
  }



  public MazeStatus processBattleMazeResult(String username, BattleNormal result, int mazeId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.gameGetUserInfo(username, false);
    BattleNormalExtData ext = result.getExtData();
    MazeStatus mazeStatus = maze.gameGetMazeStatus(username, mazeId, false);
    if(ext != null) {
      BattleNormalExtData.Clear clear = ext.getClear();
      if(clear != null && clear.getIsClear() > 0) {
        int coins = clear.getCoins();
        userInfo.addCoins(coins);
        int cardId = clear.getCardId();
        MkbAccount account = accountService.findAccountByUsername(username);
        account.addNewCard(cardId);
        mazeStatus.clear();
        account.setMazeStatus(mazeId, mazeStatus);
        accountService.saveAccount(account);
        LOG.info("{} has cleared maze {} and obtained {} coins and {} as clear maze reward", userInfo, mazeId, coins, card.gameGetCardDetail(username, cardId));
      }
    }
    processBattleResult(username, result);
    return mazeStatus;
  }

}
