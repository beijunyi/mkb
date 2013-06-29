package im.grusis.mkb.core.emulator;

import java.util.LinkedHashMap;
import java.util.Map;

import im.grusis.mkb.core.emulator.game.model.basic.*;
import im.grusis.mkb.core.emulator.game.model.response.*;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.service.AccountService;
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
public class EmulatorMelee {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorMelee.class);

  @Autowired AccountService accountService;
  @Autowired EmulatorCore core;
  @Autowired EmulatorUser user;
  @Autowired EmulatorCard card;

  public boolean gameSetMeleeCardGroup(String username, int type, int prizeCardId, int... otherCardId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    StringBuilder sb = new StringBuilder();
    for(int id : otherCardId) {
      if(sb.length() > 0) {
        sb.append('_');
      }
      sb.append(id);
    }
    params.put("PrizeCardId", Integer.toString(prizeCardId));
    params.put("type", Integer.toString(type));
    params.put("OtherCardId", sb.toString());
    MeleeSetCardGroupResponse response = core.gameDoAction(username, "melee.php", "SetCardGroup", params, MeleeSetCardGroupResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }

  public MeleeInfo gameMeleeGetInfo(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.gameGetUserInfo(username, false);
    LOG.debug("{} is retrieving melee event information", userInfo);
    MeleeGetInfoResponse response = core.gameDoAction(username, "melee.php", "GetInfo", null, MeleeGetInfoResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    MeleeInfo info = response.getData();
    LOG.info("{} has retrieved melee event information", userInfo);
    return info;
  }

  public MeleeApplyResult gameMeleeApply(String username, int type) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.gameGetUserInfo(username, false);
    LOG.debug("{} is applying for a type {} melee event", userInfo, type);
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("type", Integer.toString(type));
    MeleeApplyResponse response = core.gameDoAction(username, "melee.php", "Apply", params, MeleeApplyResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    MeleeApplyResult result = response.getData();
    String[] ids = result.getCardAll().split("_");
    StringBuilder sb = new StringBuilder();
    for(String id : ids) {
      if(sb.length() > 0) {
        sb.append(", ");
      }
      sb.append(id).append(' ').append(card.gameGetCardDetail(username, Integer.parseInt(id)).getCardName());
    }
    LOG.info("{} obtained {} from type {} melee event", userInfo, sb, type);
    return result;
  }

  public MeleeCardGroup gameMeleeGetCardGroup(String username, int type) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.gameGetUserInfo(username, false);
    LOG.debug("{} is retrieving type {} melee event card group information", userInfo, type);
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("type", Integer.toString(type));
    params.put("Uid", Long.toString(userInfo.getUid()));
    MeleeGetCardGroupResponse response = core.gameDoAction(username, "melee.php", "GetCardGroup", params, MeleeGetCardGroupResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    MeleeCardGroup cardGroup = response.getData();
    LOG.info("{} has retrieved card group information for type {} melee event", userInfo, type);
    return cardGroup;
  }
}
