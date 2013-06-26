package im.grusis.mkb.web.model;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-6-26
 * Time: 下午11:19
 */
public class ClearCounterAttackRequest {
  private String username;
  private List<Integer> stageIds;
  private int maxTry;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<Integer> getStageIds() {
    return stageIds;
  }

  public void setStageIds(List<Integer> stageIds) {
    this.stageIds = stageIds;
  }

  public int getMaxTry() {
    return maxTry;
  }

  public void setMaxTry(int maxTry) {
    this.maxTry = maxTry;
  }
}
