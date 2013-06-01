package im.grusis.mkb.emulator.emulator.core.model.response;

import java.util.Map;

import im.grusis.mkb.emulator.emulator.core.model.basic.GameData;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 下午8:28
 */
public class EditFreshResponse extends GameData<Map<String, String>> {
  public static final String AlreadyFinishedMessage = "此阶段新手引导已完成!";

  public boolean alreadyFinished() {
    String message = getMessage();
    return message != null && message.equals(AlreadyFinishedMessage);
  }
}
