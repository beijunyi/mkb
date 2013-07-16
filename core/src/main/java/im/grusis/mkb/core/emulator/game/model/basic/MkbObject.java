package im.grusis.mkb.core.emulator.game.model.basic;

import im.grusis.mkb.core.emulator.game.model.response.HasTimestamp;

public class MkbObject implements HasTimestamp {
  private long timestamp = System.currentTimeMillis();

  @Override
  public long getTimestamp() {
    return timestamp;
  }
}
