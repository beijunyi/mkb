package im.grusis.mkb.eco.service;

import im.grusis.mkb.core.emulator.MkbEmulator;
import im.grusis.mkb.eco.engines.ProductionEngine;
import im.grusis.mkb.core.service.AccountService;
import im.grusis.mkb.eco.bot.AccountBot;
import im.grusis.mkb.eco.bot.model.AccountBotProgress;
import im.grusis.mkb.eco.bot.model.AccountBotSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * User: Mothership
 * Date: 13-6-29
 * Time: 下午11:40
 */
@Service
public class BotService {
  @Autowired MkbEmulator emulator;
  @Autowired DictionaryService dictionaryService;
  @Autowired AccountService accountService;
  @Autowired ProductionEngine productionEngine;

  public AccountBot newAccountBot(AccountBotSettings settings, AccountBotProgress progress) {
    return new AccountBot(settings, progress, emulator, dictionaryService, accountService, productionEngine);
  }
}
