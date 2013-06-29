package im.grusis.mkb.eco.bot;

import im.grusis.mkb.core.emulator.EmulatorBoss;
import im.grusis.mkb.core.emulator.game.model.basic.BossFight;
import im.grusis.mkb.core.emulator.game.model.basic.BossUpdate;
import im.grusis.mkb.core.exception.MkbException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BossBot extends MkbBot<BossUpdate> {

  private final static Logger LOG = LoggerFactory.getLogger(BossBot.class);

  private String username;
  private EmulatorBoss boss;

  public BossBot(String username, EmulatorBoss boss) {
    this.username = username;
    this.boss = boss;
  }

  @Override
  synchronized protected BossUpdate bot() throws MkbException {
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
