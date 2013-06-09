package im.grusis.mkb.emulator.emulator.core.model.response;

import im.grusis.mkb.emulator.emulator.core.model.basic.Explore;
import im.grusis.mkb.emulator.emulator.core.model.basic.GameData;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 下午8:28
 */
public class ExploreResponse extends GameData<Explore> {

  public static final String StageNotAvailableMessage = "关卡尚未开启";  // 关卡尚未开启!

  public boolean stageNotAvailable() {
    String message = getMessage();
    return message != null && message.contains(StageNotAvailableMessage);
  }
}
