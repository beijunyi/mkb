package im.grusis.mkb.emulator.emulator.core.model.basic;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-6-7
 * Time: 下午10:29
 */
public class Player {
  private long Uid;
  private String NickName;
  private int Avatar;
  private int Sex;
  private int Level;
  private int HP;
  private List<BattleCard> Cards;
  private List<BattleRune> Runes;
  private int RemainHP;

  public long getUid() {
    return Uid;
  }

  public void setUid(long uid) {
    Uid = uid;
  }

  public String getNickName() {
    return NickName;
  }

  public void setNickName(String nickName) {
    NickName = nickName;
  }

  public int getAvatar() {
    return Avatar;
  }

  public void setAvatar(int avatar) {
    Avatar = avatar;
  }

  public int getSex() {
    return Sex;
  }

  public void setSex(int sex) {
    Sex = sex;
  }

  public int getLevel() {
    return Level;
  }

  public void setLevel(int level) {
    Level = level;
  }

  public int getHP() {
    return HP;
  }

  public void setHP(int HP) {
    this.HP = HP;
  }

  public List<BattleCard> getCards() {
    return Cards;
  }

  public void setCards(List<BattleCard> cards) {
    Cards = cards;
  }

  public List<BattleRune> getRunes() {
    return Runes;
  }

  public void setRunes(List<BattleRune> runes) {
    Runes = runes;
  }

  public int getRemainHP() {
    return RemainHP;
  }

  public void setRemainHP(int remainHP) {
    RemainHP = remainHP;
  }

  public class BattleCard {
    private String UUID;
    private int CardId;
    private long UserCardId;
    private int Attack;
    private int HP;
    private int Wait;
    private int Level;
    private String SkillNew;
    private String Evolution;
    private String WashTime;

    public String getUUID() {
      return UUID;
    }

    public void setUUID(String UUID) {
      this.UUID = UUID;
    }

    public int getCardId() {
      return CardId;
    }

    public void setCardId(int cardId) {
      CardId = cardId;
    }

    public long getUserCardId() {
      return UserCardId;
    }

    public void setUserCardId(long userCardId) {
      UserCardId = userCardId;
    }

    public int getAttack() {
      return Attack;
    }

    public void setAttack(int attack) {
      Attack = attack;
    }

    public int getHP() {
      return HP;
    }

    public void setHP(int HP) {
      this.HP = HP;
    }

    public int getWait() {
      return Wait;
    }

    public void setWait(int wait) {
      Wait = wait;
    }

    public int getLevel() {
      return Level;
    }

    public void setLevel(int level) {
      Level = level;
    }

    public String getSkillNew() {
      return SkillNew;
    }

    public void setSkillNew(String skillNew) {
      SkillNew = skillNew;
    }

    public String getEvolution() {
      return Evolution;
    }

    public void setEvolution(String evolution) {
      Evolution = evolution;
    }

    public String getWashTime() {
      return WashTime;
    }

    public void setWashTime(String washTime) {
      WashTime = washTime;
    }
  }

  public class BattleRune {
    private String UUID;
    private int RuneId;
    private long UserRuneId;
    private int Level;

    public String getUUID() {
      return UUID;
    }

    public void setUUID(String UUID) {
      this.UUID = UUID;
    }

    public int getRuneId() {
      return RuneId;
    }

    public void setRuneId(int runeId) {
      RuneId = runeId;
    }

    public long getUserRuneId() {
      return UserRuneId;
    }

    public void setUserRuneId(long userRuneId) {
      UserRuneId = userRuneId;
    }

    public int getLevel() {
      return Level;
    }

    public void setLevel(int level) {
      Level = level;
    }
  }
}
