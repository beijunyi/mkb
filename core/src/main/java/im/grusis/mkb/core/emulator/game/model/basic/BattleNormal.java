package im.grusis.mkb.core.emulator.game.model.basic;

/**
 * User: Mothership
 * Date: 13-6-8
 * Time: 上午1:29
 */
public class BattleNormal extends Battle<BattleNormalExtData> {
  public boolean mazeClear() {
    return getExtData().getClear().getIsClear() != 0;
  }
}
