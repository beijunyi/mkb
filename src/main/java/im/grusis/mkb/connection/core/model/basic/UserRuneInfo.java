package im.grusis.mkb.connection.core.model.basic;

/**
 * User: Mothership
 * Date: 13-5-29
 * Time: 下午11:40
 */
public class UserRuneInfo {
  private long Uid;
  private int RuneId;
  private int Level;
  private long Exp;
  private long UserRuneId;

  public long getUid() {
    return Uid;
  }

  public void setUid(long uid) {
    Uid = uid;
  }

  public int getRuneId() {
    return RuneId;
  }

  public void setRuneId(int runeId) {
    RuneId = runeId;
  }

  public int getLevel() {
    return Level;
  }

  public void setLevel(int level) {
    Level = level;
  }

  public long getExp() {
    return Exp;
  }

  public void setExp(long exp) {
    Exp = exp;
  }

  public long getUserRuneId() {
    return UserRuneId;
  }

  public void setUserRuneId(long userRuneId) {
    UserRuneId = userRuneId;
  }
}
