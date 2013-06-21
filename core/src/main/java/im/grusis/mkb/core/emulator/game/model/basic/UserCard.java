package im.grusis.mkb.core.emulator.game.model.basic;

/**
 * User: Mothership
 * Date: 13-5-29
 * Time: 下午11:40
 */
public class UserCard {
  private long Uid;
  private int CardId;
  private int Level;
  private long Exp;
  private long UserCardId;

  public long getUid() {
    return Uid;
  }

  public int getCardId() {
    return CardId;
  }

  public int getLevel() {
    return Level;
  }

  public long getExp() {
    return Exp;
  }

  public long getUserCardId() {
    return UserCardId;
  }
}
