package im.grusis.mkb.core.emulator.game.model.basic;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-6-20
 * Time: 上午12:01
 */
public class ArenaCompetitors extends MkbObject {
  private List<ArenaCompetitor> Competitors;
  private int Countdown;

  public List<ArenaCompetitor> getCompetitors() {
    return Competitors;
  }

  public int getCountdown() {
    return Countdown;
  }
}
