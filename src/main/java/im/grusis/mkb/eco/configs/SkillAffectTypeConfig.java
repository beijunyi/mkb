package im.grusis.mkb.eco.configs;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-6-17
 * Time: 上午12:54
 */
public class SkillAffectTypeConfig {
  public static final String SkillAffectTypeConfigPrefix = "skill.affectType.";
  public static final String SubTypes = ".type";
  public static final String SubType = ".type.";
  public static final String ScoreBase = ".base.";
  public static final String ScoreDiscount = ".discount";

  private int affectType;
  private int score;
  private List<ScoreBase> scoreBases;
  private List<SubType> subTypes;

  public SkillAffectTypeConfig(int affectType, List<im.grusis.mkb.eco.configs.SubType> subTypes) {
    this.affectType = affectType;
    this.subTypes = subTypes;
  }

  public SkillAffectTypeConfig(int affectType, int score, List<ScoreBase> scoreBases) {
    this.affectType = affectType;
    this.score = score;
    this.scoreBases = scoreBases;
  }

  public static String getSkillAffectTypeConfigPrefix() {
    return SkillAffectTypeConfigPrefix;
  }

  public int getAffectType() {
    return affectType;
  }

  public int getScore() {
    return score;
  }

  public List<ScoreBase> getScoreBases() {
    return scoreBases;
  }

  public List<SubType> getSubTypes() {
    return subTypes;
  }




}
