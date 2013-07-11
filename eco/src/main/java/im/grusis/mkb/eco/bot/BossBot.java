package im.grusis.mkb.eco.bot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import im.grusis.mkb.core.emulator.EmulatorBoss;
import im.grusis.mkb.core.emulator.EmulatorCard;
import im.grusis.mkb.core.emulator.game.model.basic.BossFight;
import im.grusis.mkb.core.emulator.game.model.basic.BossUpdate;
import im.grusis.mkb.core.exception.MkbException;
import im.grusis.mkb.eco.engines.CardEngine;
import im.grusis.mkb.eco.model.BossSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BossBot extends MkbBot<BossUpdate> {

  private final static Logger LOG = LoggerFactory.getLogger(BossBot.class);

  private String username;
  private BossSettings bossSettings;
  private CardEngine cardEngine;
  private EmulatorBoss boss;

  public BossBot(String username, BossSettings bossSettings, CardEngine cardEngine, EmulatorBoss boss) {
    this.username = username;
    this.bossSettings = bossSettings;
    this.cardEngine = cardEngine;
    this.boss = boss;
  }

  @Override
  synchronized protected BossUpdate bot() throws MkbException {
    int cardGroupIndex = bossSettings.getSpecialCardGroup();
    cardEngine.switchCardGroup(username, cardGroupIndex);
    BossUpdate bossUpdate = null;
    do {
      BossFight bossFight = boss.fight(username);
      if(bossFight != null) {
        int interval = bossFight.getCanFightTime();
        try {
          wait(interval * 1000);
        } catch(InterruptedException e) {
          LOG.error("*** UNKNOWN ERROR ***", e);
        }
      } else {
        bossUpdate = boss.getBoss(username);
      }
    } while(bossUpdate == null || (bossUpdate.getBoss().getBossCurrentHp() > 0 && bossUpdate.getBossFleeTime() > 0));
    return bossUpdate;
  }
}
