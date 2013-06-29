package im.grusis.mkb.core.emulator;

import im.grusis.mkb.core.emulator.game.model.basic.ChipPuzzle;
import im.grusis.mkb.core.emulator.game.model.response.ChipGetUserChipResponse;
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
 * Date: 13-6-28
 * Time: 下午11:28
 */
@Component
public class EmulatorChip {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorChip.class);

  @Autowired AccountService accountService;
  @Autowired EmulatorCore core;

  public ChipPuzzle gameGetUserChip(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    ChipPuzzle userChip;
    if(refresh || (userChip = account.getUserChip()) == null) {
      ChipGetUserChipResponse response = core.gameDoAction(username, "chip.php", "GetUserChip", null, ChipGetUserChipResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      userChip = response.getData();
      account.setUserChip(userChip);
      accountService.saveAccount(account);
    }
    return userChip;
  }
}
