package im.grusis.mkb.core.emulator.game.model.basic;

import java.util.TreeMap;

import im.grusis.mkb.core.emulator.game.model.response.HasTimestamp;

public class UserMapStages extends TreeMap<Integer, UserMapStage> implements HasTimestamp{
  private long timestamp = System.currentTimeMillis();

  @Override
  public long getTimestamp() {
    return timestamp;
  }
}
