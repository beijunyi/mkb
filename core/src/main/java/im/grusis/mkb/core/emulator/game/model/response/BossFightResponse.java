package im.grusis.mkb.core.emulator.game.model.response;

import im.grusis.mkb.core.emulator.game.model.basic.BossFight;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 下午8:28
 */
public class BossFightResponse extends GameData<BossFight> {
  public static final String AlreadyInQueueMessage = "您已经在等待队列中"; // 您已经在等待队列中!
  public static final String BossUnavailableMessage = "Boss还未刷新或已逃走"; // Boss还未刷新或已逃走
  public static final String BossDownMessage = "有手快的已经把Boss打掉啦"; // 有手快的已经把Boss打掉啦~

  public boolean isAlreadyInQueue() {
    return message != null && message.contains(AlreadyInQueueMessage);
  }

  public boolean isBossUnavailable() {
    return message != null && message.contains(BossUnavailableMessage);
  }

  public boolean isBossDown() {
    return message != null && message.contains(BossDownMessage);
  }
}
