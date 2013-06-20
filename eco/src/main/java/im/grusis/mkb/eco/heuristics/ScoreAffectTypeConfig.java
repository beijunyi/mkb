package im.grusis.mkb.eco.heuristics;

import java.util.List;
import java.util.Map;

/**
 * User: Mothership
 * Date: 13-6-17
 * Time: 上午12:54
 */
public class ScoreAffectTypeConfig {
  public static final String SkillAffectTypeConfigPrefix = "score.affectType.";
  public static final String SubTypes = ".type";
  public static final String SubType = ".type.";
  public static final String ScoreBase = ".base.";
  public static final String ScoreDiscount = ".discount";
  public static final String ScoreInverse = ".inverse";

  private int affectType;
  private int score;
  private List<ScoreBase> scoreBases;
  private Map<Integer, SubType> subTypes;

  public ScoreAffectTypeConfig(int affectType, Map<Integer, im.grusis.mkb.eco.heuristics.SubType> subTypes) {
    this.affectType = affectType;
    this.subTypes = subTypes;
  }

  public ScoreAffectTypeConfig(int affectType, int score, List<ScoreBase> scoreBases) {
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

  public Map<Integer, SubType> getSubTypes() {
    return subTypes;
  }
}
