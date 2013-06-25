package im.grusis.mkb.eco.util.filter.common;

import im.grusis.mkb.core.emulator.game.model.basic.UserLegion;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.util.AccountFilter;

/**
 * User: Mothership
 * Date: 13-6-25
 * Time: 下午10:12
 */
public class LegionNameFilter implements AccountFilter {
  private String legionName;

  public LegionNameFilter(String legionName) {
    this.legionName = legionName;
  }

  @Override
  public boolean accept(MkbAccount account) {
    UserLegion userLegion = account.getUserLegion();
    return userLegion != null && userLegion.getLegionName().contains(legionName);
  }
}
