package im.grusis.mkb.core.emulator.game.model.basic;

import java.util.ArrayList;

import im.grusis.mkb.core.emulator.game.model.response.HasTimestamp;

/**
 * User: Mothership
 * Date: 13-5-29
 * Time: 下午11:59
 */
public class GoodsList extends ArrayList<Goods> implements HasTimestamp {
  private long timestamp = System.currentTimeMillis();

  @Override
  public long getTimestamp() {
    return timestamp;
  }
}
