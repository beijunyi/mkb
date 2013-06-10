package im.grusis.mkb.emulator.emulator.core.model.response;

import im.grusis.mkb.emulator.emulator.core.model.basic.Battle;

/**
 * User: Mothership
 * Date: 13-6-10
 * Time: 上午12:46
 */
public abstract class BattleResponse<T extends Battle> extends EnergyUsingResponse<T> {
  public static final String InvalidBattleMessage = "没有战斗信息"; // 没有战斗信息!

  public boolean invalidBattle() {
    return message != null && message.contains(InvalidBattleMessage);
  }
}
