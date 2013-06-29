package im.grusis.mkb.core.emulator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import im.grusis.mkb.core.emulator.game.model.basic.Streng;
import im.grusis.mkb.core.emulator.game.model.response.StrengCardResponse;
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
public class EmulatorStreng {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorStreng.class);

  @Autowired AccountService accountService;
  @Autowired EmulatorCore core;
  @Autowired EmulatorUser user;


  public Streng gameUpgradeCard(String username, long targetUserCardId, List<Long> sourceUserCardIds) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    StringBuilder sb = new StringBuilder();
    for(long sourceId : sourceUserCardIds) {
      if(sb.length() > 0) {
        sb.append("_");
      }
      sb.append(sourceId);
    }
    params.put("UserCardId2", sb.toString());
    params.put("UserCardId1", Long.toString(targetUserCardId));
    StrengCardResponse response = core.gameDoAction(username, "streng.php", "Card", params, StrengCardResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

}
