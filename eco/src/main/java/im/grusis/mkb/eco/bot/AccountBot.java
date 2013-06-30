package im.grusis.mkb.eco.bot;

import im.grusis.mkb.core.emulator.ItemCode;
import im.grusis.mkb.core.emulator.MkbEmulator;
import im.grusis.mkb.eco.engines.ProductionEngine;
import im.grusis.mkb.core.emulator.game.model.basic.UserInfo;
import im.grusis.mkb.core.exception.MkbException;
import im.grusis.mkb.core.service.AccountService;
import im.grusis.mkb.eco.bot.model.AccountBotProgress;
import im.grusis.mkb.eco.bot.model.AccountBotSettings;
import im.grusis.mkb.eco.service.DictionaryService;
import im.grusis.mkb.eco.util.dictionary.BasicNicknameDict;
import im.grusis.mkb.eco.util.dictionary.BasicUsernameDict;
import im.grusis.mkb.eco.util.password.HashPasswordGenerator;
import org.apache.commons.lang3.StringUtils;

/**
 * User: Mothership
 * Date: 13-6-29
 * Time: 下午4:04
 */
public class AccountBot extends MkbBot<AccountBotProgress> {

  private AccountBotSettings settings;

  private MkbEmulator emulator;
  private DictionaryService dictionaryService;
  private AccountService accountService;
  private ProductionEngine productionEngine;
  private AccountBotProgress progress;

  public AccountBot(AccountBotSettings settings, AccountBotProgress progress, MkbEmulator emulator, DictionaryService dictionaryService, AccountService accountService, ProductionEngine productionEngine) {
    this.settings = settings;
    this.emulator = emulator;
    this.dictionaryService = dictionaryService;
    this.accountService = accountService;
    this.productionEngine = productionEngine;
    this.progress = progress;
  }

  @Override
  protected AccountBotProgress bot() throws MkbException {
    BasicUsernameDict usernameDict = dictionaryService.newBasicUsernameDict(settings.getUsernamePrefix());
    BasicNicknameDict nicknameDict = dictionaryService.newBasicNicknameDict(settings.getNicknamePrefix());
    String defaultPassword = settings.getPassword();
    HashPasswordGenerator passwordGenerator = new HashPasswordGenerator();
    String invitor = null;
    String inviteCode = settings.getInviteCode();
    while(progress.getAccounts().size() < settings.getTotal() && usernameDict.hasNext() && nicknameDict.hasNext()) {
      String username = usernameDict.next();
      String password;
      if(StringUtils.trimToNull(defaultPassword) == null) {
        password = passwordGenerator.generate(username);
      } else {
        password = defaultPassword;
      }
      boolean success = productionEngine.registerNewAccount(username, password, settings.getGender(), nicknameDict, settings.getServerId(), inviteCode);
      if(success) {
        progress.addAccount(accountService.findAccountByUsername(username));
        if(invitor != null) {
          UserInfo invitorInfo = emulator.user().getUserInfo(username, false);
          invitorInfo.addInviteNum();
          if(invitorInfo.getInviteNum() == 1) {
            emulator.user().awardSalary(invitor);
            emulator.shop().buy(invitor, ItemCode.Ticket);
          }
        }
        if(!settings.isUseSameInviteCode()) {
          invitor = username;
          inviteCode = emulator.user().getUserInfo(invitor, false).getInviteCode();
        }
      }
    }
    progress.finish();
    dictionaryService.updateBasicUsernameDictRecord(usernameDict);
    dictionaryService.updateBasicNicknameDictRecord(nicknameDict);
    return progress;
  }
}
