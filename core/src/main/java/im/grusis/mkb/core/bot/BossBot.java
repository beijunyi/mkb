package im.grusis.mkb.core.bot;

import im.grusis.mkb.core.emulator.MkbEmulator;
import im.grusis.mkb.core.emulator.game.model.basic.BossFight;
import im.grusis.mkb.core.emulator.game.model.basic.BossUpdate;
import im.grusis.mkb.core.emulator.game.model.basic.UserInfo;
import im.grusis.mkb.core.exception.MkbException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BossBot extends MkbBot<Integer> {

  private final static Logger LOG = LoggerFactory.getLogger(BossBot.class);

  public BossBot(String username, MkbEmulator emulator) {
    super(username, emulator);
  }

  @Override
  synchronized protected Integer bot() throws MkbException {
    BossUpdate bossUpdate = null;
    do {
      BossFight bossFight = emulator.gameBossFight(username);
      if(bossFight != null) {
        int interval = bossFight.getCanFightTime();
        try {
          wait(interval * 1000);
        } catch(InterruptedException e) {
          UserInfo userInfo = emulator.gameGetUserInfo(username, false);
          LOG.error(userInfo + " cannot continue with boss bot", e);
        }
      } else {
        bossUpdate = emulator.gameGetBossUpdate(username);
      }
    } while(bossUpdate == null || false);
    return null;
  }
}
