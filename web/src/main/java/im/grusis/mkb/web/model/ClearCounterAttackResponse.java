package im.grusis.mkb.web.model;

import java.util.Collection;
import java.util.Set;

import im.grusis.mkb.core.emulator.game.model.basic.UserMapStage;

/**
 * User: Mothership
 * Date: 13-6-26
 * Time: 下午11:19
 */
public class ClearCounterAttackResponse {
  private Collection<UserMapStage> userMapStages;

  public ClearCounterAttackResponse(Set<UserMapStage> userMapStages) {
    this.userMapStages = userMapStages;
  }

  public Collection<UserMapStage> getUserMapStages() {
    return userMapStages;
  }
}
