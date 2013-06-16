package im.grusis.mkb.emulator.emulator.core.model.basic;

/**
 * User: Mothership
 * Date: 13-6-16
 * Time: 下午1:09
 */
public class SalaryInfo {

  public static final int TYpe_Coin = 1;
  public static final int Type_Card = 4;

  private int Type;
  private int AwardType;
  private int AwardValue;
  private long Time;
  private int SalaryId;

  public int getType() {
    return Type;
  }

  public int getAwardType() {
    return AwardType;
  }

  public int getAwardValue() {
    return AwardValue;
  }

  public long getTime() {
    return Time;
  }

  public int getSalaryId() {
    return SalaryId;
  }
}
