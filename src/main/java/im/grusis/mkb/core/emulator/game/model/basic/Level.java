package im.grusis.mkb.core.emulator.game.model.basic;

/**
 * User: Mothership
 * Date: 13-6-7
 * Time: 下午9:50
 */
public class Level {
  private int MapStageDetailId;
  private int Level;
  private String CardList;
  private String RuneList;
  private int HeroLevel;
  private int AchievementId;
  private int EnergyExpend;
  private String BonusWin;
  private String FirstBonusWin;
  private String BonusLose;
  private String AddedBonus;
  private int EnergyExplore;
  private String BonusExplore;
  private int Hint;
  private String AchievementText;

  public int getMapStageDetailId() {
    return MapStageDetailId;
  }

  public void setMapStageDetailId(int mapStageDetailId) {
    MapStageDetailId = mapStageDetailId;
  }

  public int getLevel() {
    return Level;
  }

  public void setLevel(int level) {
    Level = level;
  }

  public String getCardList() {
    return CardList;
  }

  public void setCardList(String cardList) {
    CardList = cardList;
  }

  public String getRuneList() {
    return RuneList;
  }

  public void setRuneList(String runeList) {
    RuneList = runeList;
  }

  public int getHeroLevel() {
    return HeroLevel;
  }

  public void setHeroLevel(int heroLevel) {
    HeroLevel = heroLevel;
  }

  public int getAchievementId() {
    return AchievementId;
  }

  public void setAchievementId(int achievementId) {
    AchievementId = achievementId;
  }

  public int getEnergyExpend() {
    return EnergyExpend;
  }

  public void setEnergyExpend(int energyExpend) {
    EnergyExpend = energyExpend;
  }

  public String getBonusWin() {
    return BonusWin;
  }

  public void setBonusWin(String bonusWin) {
    BonusWin = bonusWin;
  }

  public String getFirstBonusWin() {
    return FirstBonusWin;
  }

  public void setFirstBonusWin(String firstBonusWin) {
    FirstBonusWin = firstBonusWin;
  }

  public String getBonusLose() {
    return BonusLose;
  }

  public void setBonusLose(String bonusLose) {
    BonusLose = bonusLose;
  }

  public String getAddedBonus() {
    return AddedBonus;
  }

  public void setAddedBonus(String addedBonus) {
    AddedBonus = addedBonus;
  }

  public int getEnergyExplore() {
    return EnergyExplore;
  }

  public void setEnergyExplore(int energyExplore) {
    EnergyExplore = energyExplore;
  }

  public String getBonusExplore() {
    return BonusExplore;
  }

  public void setBonusExplore(String bonusExplore) {
    BonusExplore = bonusExplore;
  }

  public int getHint() {
    return Hint;
  }

  public void setHint(int hint) {
    Hint = hint;
  }

  public String getAchievementText() {
    return AchievementText;
  }

  public void setAchievementText(String achievementText) {
    AchievementText = achievementText;
  }
}
