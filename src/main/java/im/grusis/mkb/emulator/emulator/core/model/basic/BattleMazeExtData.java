package im.grusis.mkb.emulator.emulator.core.model.basic;

/**
 * User: Mothership
 * Date: 13-6-7
 * Time: 下午10:25
 */
public class BattleMazeExtData extends BattleExtData{

  private Award Award;
  private Clear Clear;
  private User User;

  public BattleMazeExtData.Award getAward() {
    return Award;
  }

  public void setAward(BattleMazeExtData.Award award) {
    Award = award;
  }

  public BattleMazeExtData.Clear getClear() {
    return Clear;
  }

  public void setClear(BattleMazeExtData.Clear clear) {
    Clear = clear;
  }

  public BattleMazeExtData.User getUser() {
    return User;
  }

  public void setUser(BattleMazeExtData.User user) {
    User = user;
  }

  public class Award {
    private int Coins;
    private int Exp;
    private int CardId;
    private int Chip;

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

    public int getChip() {
      return Chip;
    }

    public void setChip(int chip) {
      Chip = chip;
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
