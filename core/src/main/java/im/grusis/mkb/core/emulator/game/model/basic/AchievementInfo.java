package im.grusis.mkb.core.emulator.game.model.basic;

/**
 * User: Mothership
 * Date: 13-6-9
 * Time: 上午12:53
 */
public class AchievementInfo extends MkbObject {
  private int AchievementId;
  private String Condition;
  private String Name;
  private String Amount;
  private String Pic;

  public int getAchievementId() {
    return AchievementId;
  }

  public void setAchievementId(int achievementId) {
    AchievementId = achievementId;
  }

  public String getCondition() {
    return Condition;
  }

  public void setCondition(String condition) {
    Condition = condition;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getAmount() {
    return Amount;
  }

  public void setAmount(String amount) {
    Amount = amount;
  }

  public String getPic() {
    return Pic;
  }

  public void setPic(String pic) {
    Pic = pic;
  }
}
