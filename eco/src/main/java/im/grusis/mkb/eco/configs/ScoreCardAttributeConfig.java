package im.grusis.mkb.eco.configs;

/**
 * User: Mothership
 * Date: 13-6-17
 * Time: 下午9:41
 */
public class ScoreCardAttributeConfig {

  public static final String CardAttributeConfigPrefix = "score.card.";
  public static final String AttackDiscount = "atkDiscount";
  public static final String HPDiscount = "hpDiscount";
  public static final String HPPenalty = "hpPenalty";
  public static final String HPPenaltyBase = "hpPenaltyBase";
  public static final String CDBase = "cdBase";
  public static final String CDDiscount = "cdDiscount";
  public static final String CDPenalty = "cdPenalty";
  public static final String CDPenaltyException = "cdPenaltyException";

  private double atkDiscount;
  private double hpDiscount;
  private double hpPenalty;
  private double hpPenaltyBase;
  private int cdBase;
  private double cdDiscount;
  private int cdPenalty;
  private int[] cdPenaltyExceptions;

  public ScoreCardAttributeConfig(double atkDiscount, double hpDiscount, double hpPenalty, double hpPenaltyBase, int cdBase, double cdDiscount, int cdPenalty, String cdPenaltyException) {
    this.atkDiscount = atkDiscount;
    this.hpDiscount = hpDiscount;
    this.hpPenalty = hpPenalty;
    this.hpPenaltyBase = hpPenaltyBase;
    this.cdBase = cdBase;
    this.cdDiscount = cdDiscount;
    this.cdPenalty = cdPenalty;
    String[] exceptions = cdPenaltyException.split(",");
    cdPenaltyExceptions = new int[exceptions.length];
    for(int i = 0; i < exceptions.length; i++) {
      cdPenaltyExceptions[i] = Integer.parseInt(exceptions[i]);
    }
  }

  public double getAtkDiscount() {
    return atkDiscount;
  }

  public double getHpDiscount() {
    return hpDiscount;
  }

  public double getHpPenalty() {
    return hpPenalty;
  }

  public double getHpPenaltyBase() {
    return hpPenaltyBase;
  }

  public int getCdBase() {
    return cdBase;
  }

  public double getCdDiscount() {
    return cdDiscount;
  }

  public int getCdPenalty() {
    return cdPenalty;
  }

  public int[] getCdPenaltyExceptions() {
    return cdPenaltyExceptions;
  }
}
