package im.grusis.mkb.emulator.emulator.core.model.basic;

/**
 * User: Mothership
 * Date: 13-6-16
 * Time: 下午2:17
 */
public class LegionAttackDetail {
  private int Id;
  private String Name;
  private int LegionId;
  private int LegionName;
  private int Emblem;
  private int EmblemLevel;
  private String Coins;
  private LegionInfo AttackLegion;
  private LegionInfo DefendLegion;
  private boolean IsJoin;
  private int UserLegionId;
  private int Status;
  private String Time;

  public int getId() {
    return Id;
  }

  public String getName() {
    return Name;
  }

  public int getLegionId() {
    return LegionId;
  }

  public int getLegionName() {
    return LegionName;
  }

  public int getEmblem() {
    return Emblem;
  }

  public int getEmblemLevel() {
    return EmblemLevel;
  }

  public String getCoins() {
    return Coins;
  }

  public LegionInfo getAttackLegion() {
    return AttackLegion;
  }

  public LegionInfo getDefendLegion() {
    return DefendLegion;
  }

  public boolean isIsJoin() {
    return IsJoin;
  }

  public int getUserLegionId() {
    return UserLegionId;
  }

  public int getStatus() {
    return Status;
  }

  public String getTime() {
    return Time;
  }
}
