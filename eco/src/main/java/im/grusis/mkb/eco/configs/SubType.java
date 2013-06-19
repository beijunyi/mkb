package im.grusis.mkb.eco.configs;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-6-17
 * Time: 上午1:22
 */
public class SubType {
  private int type;
  private int score;
  private List<ScoreBase> scoreBases;

  public SubType(int type, int score, List<ScoreBase> scoreBases) {
    this.type = type;
    this.score = score;
    this.scoreBases = scoreBases;
  }

  public int getType() {
    return type;
  }

  public int getScore() {
    return score;
  }

  public List<ScoreBase> getScoreBases() {
    return scoreBases;
  }
}