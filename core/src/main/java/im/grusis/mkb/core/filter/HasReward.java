package im.grusis.mkb.core.filter;

import im.grusis.mkb.core.emulator.game.model.basic.UserInfo;
import im.grusis.mkb.core.repository.model.MkbAccount;

/**
 * User: Mothership
 * Date: 13-6-16
 * Time: 上午1:52
 */
public class HasReward implements AccountFilter{

  @Override
  public boolean accept(MkbAccount account) {
    UserInfo userInfo = account.getUserInfo();
    return userInfo != null && userInfo.getSalaryCount() > 0;
  }
}
