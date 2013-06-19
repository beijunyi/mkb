package im.grusis.mkb.core.emulator.game.model.response;

import im.grusis.mkb.core.emulator.game.model.basic.BattleNormal;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 上午1:15
 */
public class MazeBattleResponse extends BattleResponse<BattleNormal> {
  public static final String InvalidMazeLayer = "迷宫尚未开启"; // 迷宫尚未开启.

  public boolean invalidMazeLayer() {
    return message != null && message.contains(InvalidMazeLayer);
  }
}
