package im.grusis.mkb.core.emulator;

import im.grusis.mkb.core.emulator.game.model.basic.LegionAttackInfo;
import im.grusis.mkb.core.emulator.game.model.basic.UserInfo;
import im.grusis.mkb.core.emulator.game.model.response.LegionAttackInfoResponse;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
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
public class EmulatorLegionAttack {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorLegionAttack.class);

  @Autowired EmulatorCore core;
  @Autowired EmulatorUser user;

  public LegionAttackInfo gameLegionAttackInfo(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.gameGetUserInfo(username, false);
    LOG.debug("{} is retrieving legion attack info", userInfo);
    LegionAttackInfoResponse response = core.gameDoAction(username, "legionattack.php", "info", null, LegionAttackInfoResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    LegionAttackInfo info = response.getData();
    LOG.info("{} has retrieved legion attack info", userInfo);
    return info;
  }
}
