package im.grusis.mkb.eco.configs;

/**
 * User: Mothership
 * Date: 13-6-17
 * Time: 上午1:21
 */
public class ScoreBase {
  private int base;
  private double discount;

  public ScoreBase(int base, double discount) {
    this.base = base;
    this.discount = discount;
  }

  public int getBase() {
    return base;
  }

  public double getDiscount() {
    return discount;
  }
}