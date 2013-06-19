package im.grusis.mkb.core.emulator.game.model.basic;

/**
 * User: Mothership
 * Date: 13-5-29
 * Time: 下午11:40
 */
public class UserCardInfo {
  private long Uid;
  private int CardId;
  private int Level;
  private long Exp;
  private long UserCardId;

  public long getUid() {
    return Uid;
  }

  public void setUid(long uid) {
    Uid = uid;
  }

  public int getCardId() {
    return CardId;
  }

  public void setCardId(int cardId) {
    CardId = cardId;
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

  public long getUserCardId() {
    return UserCardId;
  }

  public void setUserCardId(long userCardId) {
    UserCardId = userCardId;
  }
}
