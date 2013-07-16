package im.grusis.mkb.core.emulator.game.model.basic;

/**
 * User: Mothership
 * Date: 13-6-16
 * Time: 下午1:29
 */
public class MeleeApplyResult extends MkbObject {
  private String CardAll;
  private int ResetTime;
  private int ResetPrice;

  public String getCardAll() {
    return CardAll;
  }

  public int getResetTime() {
    return ResetTime;
  }

  public int getResetPrice() {
    return ResetPrice;
  }
}
