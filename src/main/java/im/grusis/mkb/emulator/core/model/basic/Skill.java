package im.grusis.mkb.emulator.core.model.basic;

/**
 * User Mothership
 * Date 13-5-29
 * Time 下午1133
 */
public class Skill {
  private int SkillId;
  private String Name;
  private int Type;
  private int LanchType;
  private int LanchCondition;
  private int LanchConditionValue;
  private int AffectType;
  private int AffectValue;
  private int AffectValue2;
  private int SkillCategory;
  private String Desc;

  public int getSkillId() {
    return SkillId;
  }

  public void setSkillId(int skillId) {
    SkillId = skillId;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public int getType() {
    return Type;
  }

  public void setType(int type) {
    Type = type;
  }

  public int getLanchType() {
    return LanchType;
  }

  public void setLanchType(int lanchType) {
    LanchType = lanchType;
  }

  public int getLanchCondition() {
    return LanchCondition;
  }

  public void setLanchCondition(int lanchCondition) {
    LanchCondition = lanchCondition;
  }

  public int getLanchConditionValue() {
    return LanchConditionValue;
  }

  public void setLanchConditionValue(int lanchConditionValue) {
    LanchConditionValue = lanchConditionValue;
  }

  public int getAffectType() {
    return AffectType;
  }

  public void setAffectType(int affectType) {
    AffectType = affectType;
  }

  public int getAffectValue() {
    return AffectValue;
  }

  public void setAffectValue(int affectValue) {
    AffectValue = affectValue;
  }

  public int getAffectValue2() {
    return AffectValue2;
  }

  public void setAffectValue2(int affectValue2) {
    AffectValue2 = affectValue2;
  }

  public int getSkillCategory() {
    return SkillCategory;
  }

  public void setSkillCategory(int skillCategory) {
    SkillCategory = skillCategory;
  }

  public String getDesc() {
    return Desc;
  }

  public void setDesc(String desc) {
    Desc = desc;
  }
}
