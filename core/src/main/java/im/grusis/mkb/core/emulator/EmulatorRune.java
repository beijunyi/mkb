package im.grusis.mkb.core.emulator;

import java.util.Map;

import im.grusis.mkb.core.emulator.game.model.basic.RuneDef;
import im.grusis.mkb.core.emulator.game.model.response.RuneGetAllRuneResponse;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.service.AccountService;
import im.grusis.mkb.core.service.AssetsService;
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
public class EmulatorRune {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorRune.class);

  @Autowired AssetsService assetsService;
  @Autowired AccountService accountService;
  @Autowired EmulatorCore core;

  public Map<Integer, RuneDef> getAllRune(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<Integer, RuneDef> runes;
    if(refresh || (runes = assetsService.getRuneLookup()).isEmpty()) {
      RuneGetAllRuneResponse response = core.gameDoAction(username, "rune.php", "GetAllRune", null, RuneGetAllRuneResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      runes = assetsService.saveAssets(response.getData());
    }
    return runes;
  }

  public RuneDef getRune(String username, int runeId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    RuneDef rune = assetsService.findRune(runeId);
    if(rune == null) {
      rune = getAllRune(username, true).get(runeId);
      if(rune == null) {
        throw new UnknownErrorException();
      }
    }
    return rune;
  }

}
