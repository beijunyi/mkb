package im.grusis.mkb.emulator.emulator.core.model.basic;

/**
 * User: Mothership
 * Date: 13-6-7
 * Time: 下午10:25
 */
public class BattleExtData {

  private Award Award;
  private Clear Clear;
  private User User;

  public BattleExtData.Award getAward() {
    return Award;
  }

  public void setAward(BattleExtData.Award award) {
    Award = award;
  }

  public BattleExtData.Clear getClear() {
    return Clear;
  }

  public void setClear(BattleExtData.Clear clear) {
    Clear = clear;
  }

  public BattleExtData.User getUser() {
    return User;
  }

  public void setUser(BattleExtData.User user) {
    User = user;
  }

  public class Award {
    private int Coins;
    private int Exp;
    private int CardId;

    public int getCoins() {
      return Coins;
    }

    public void setCoins(int coins) {
      Coins = coins;
    }

    public int getExp() {
      return Exp;
    }

    public void setExp(int exp) {
      Exp = exp;
    }

    public int getCardId() {
      return CardId;
    }

    public void setCardId(int cardId) {
      CardId = cardId;
    }
  }

  public class Clear {
    private int IsClear;
    private int CardId;
    private int Coins;

    public int getIsClear() {
      return IsClear;
    }

    public void setIsClear(int isClear) {
      IsClear = isClear;
    }

    public int getCardId() {
      return CardId;
    }

    public void setCardId(int cardId) {
      CardId = cardId;
    }

    public int getCoins() {
      return Coins;
    }

    public void setCoins(int coins) {
      Coins = coins;
    }
  }

  public class User {
    private int Level;
    private long Exp;
    private long PrevExp;
    private long NextExp;

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

    public long getPrevExp() {
      return PrevExp;
    }

    public void setPrevExp(long prevExp) {
      PrevExp = prevExp;
    }

    public long getNextExp() {
      return NextExp;
    }

    public void setNextExp(long nextExp) {
      NextExp = nextExp;
    }
  }
}
