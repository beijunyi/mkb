package im.grusis.mkb.core.emulator.game.model.response;

import java.util.Map;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 下午8:28
 */
public class UserEditFreshResponse extends GameData<Map<String, String>> {
  public static final String AlreadyFinishedMessage = "此阶段新手引导已完成"; // 此阶段新手引导已完成!

  public boolean alreadyFinished() {
    return message != null && message.contains(AlreadyFinishedMessage);
  }
}
