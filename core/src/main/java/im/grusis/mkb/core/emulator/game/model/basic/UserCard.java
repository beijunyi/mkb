package im.grusis.mkb.core.emulator.game.model.basic;

/**
 * User: Mothership
 * Date: 13-5-29
 * Time: 下午11:40
 */
public class UserCard {
  protected long Uid;
  protected int CardId;
  protected int Level;
  protected long Exp;
  protected long UserCardId;
  protected int SkillNew;
  protected int Evolution;
  protected int WashTime;

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

  public int getSkillNew() {
    return SkillNew;
  }

  public int getEvolution() {
    return Evolution;
  }

  public int getWashTime() {
    return WashTime;
  }
}
