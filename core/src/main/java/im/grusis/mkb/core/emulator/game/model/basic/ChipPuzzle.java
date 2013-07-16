package im.grusis.mkb.core.emulator.game.model.basic;

import java.util.TreeMap;

import im.grusis.mkb.core.emulator.game.model.response.HasTimestamp;

/**
 * User: beij
 * Date: 24/06/13
 * Time: 13:09
 */
public class ChipPuzzle extends TreeMap<Integer, Chip> implements HasTimestamp {
  private long timestamp = System.currentTimeMillis();

  @Override
  public long getTimestamp() {
    return timestamp;
  }

  public boolean addChip(int chipId) {
    get(chipId).set();
    for(Chip c : values()) {
      if(!c.obtained()) {
        return false;
      }
    }
    for(Chip c : values()) {
      c.reset();
    }
    return true;
  }
}
