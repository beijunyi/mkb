package im.grusis.mkb.core.emulator.game.model.basic;

/**
 * User: Mothership
 * Date: 13-6-16
 * Time: 下午2:07
 */
public class MeleeDetail {
  private String Winner;
  private int Sex;
  private int Avatar;
  private int Bonus;
  private int EntryFee;
  private int isApply;
  private String Details;
  private long CountDown;

  public String getWinner() {
    return Winner;
  }

  public int getSex() {
    return Sex;
  }

  public int getAvatar() {
    return Avatar;
  }

  public int getBonus() {
    return Bonus;
  }

  public int getEntryFee() {
    return EntryFee;
  }

  public int getApply() {
    return isApply;
  }

  public String getDetails() {
    return Details;
  }

  public long getCountDown() {
    return CountDown;
  }
}
