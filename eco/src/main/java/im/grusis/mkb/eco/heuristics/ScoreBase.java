package im.grusis.mkb.eco.heuristics;

/**
 * User: Mothership
 * Date: 13-6-17
 * Time: 上午1:21
 */
public class ScoreBase {
  private int base;
  private double discount;
  private boolean inverse;

  public ScoreBase(int base, double discount, boolean inverse) {
    this.base = base;
    this.discount = discount;
    this.inverse = inverse;
  }

  public int getBase() {
    return base;
  }

  public double getDiscount() {
    return discount;
  }

  public boolean isInverse() {
    return inverse;
  }
}