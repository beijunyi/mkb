package im.grusis.mkb.emulator.emulator.core.model.response;

import im.grusis.mkb.emulator.emulator.core.model.basic.BossFight;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 下午8:28
 */
public class BossFightResponse extends GameData<BossFight> {
  public static final String AlreadyInQueueMessage = "您已经在等待队列中"; // 您已经在等待队列中!

  public boolean alreadyInQueue() {
    return message != null && message.contains(AlreadyInQueueMessage);
  }
}
