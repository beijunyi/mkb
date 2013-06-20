package im.grusis.mkb.core.repository.model;

/**
 * User: Mothership
 * Date: 13-6-20
 * Time: 上午12:17
 */
public class BattleRecord {
  private int win;
  private int lose;

  public void win() {
    win++;
  }

  public void lose() {
    lose++;
  }

  public double getPercent() {
    if(win + lose == 0) {
      return 0.5d;
    }
    return (double) win / (win + lose);
  }

  public int getWin() {
    return win;
  }

  public int getLose() {
    return lose;
  }
}
