package im.grusis.mkb.emulator.emulator.core.model.basic;

/**
 * User: Mothership
 * Date: 13-6-9
 * Time: 上午12:18
 */
public class Explore extends EnergyUser {
  private String[] Bonus; // Exp_*, Coins_*, Card_*, Chip_*
  private int UserLevel;
  private long Exp;
  private long PrevExp;
  private long NextExp;
  private ThievesInfo ThievesInfo;

  public String[] getBonus() {
    return Bonus;
  }

  public void setBonus(String[] bonus) {
    Bonus = bonus;
  }

  public int getUserLevel() {
    return UserLevel;
  }

  public void setUserLevel(int userLevel) {
    UserLevel = userLevel;
  }

  public long getExp() {
    return Exp;
  }

  public void setExp(long exp) {
    Exp = exp;
  }

  public long getPrevExp() {
    return PrevExp;
  }

  public void setPrevExp(long prevExp) {
    PrevExp = prevExp;
  }

  public long getNextExp() {
    return NextExp;
  }

  public void setNextExp(long nextExp) {
    NextExp = nextExp;
  }

  public ThievesInfo getThievesInfo() {
    return ThievesInfo;
  }

  public void setThievesInfo(ThievesInfo thievesInfo) {
    ThievesInfo = thievesInfo;
  }
}
