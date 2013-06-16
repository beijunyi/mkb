package im.grusis.mkb.eco.filter;

import im.grusis.mkb.emulator.emulator.core.model.basic.UserInfo;
import im.grusis.mkb.internal.MkbAccount;

/**
 * User: Mothership
 * Date: 13-6-16
 * Time: 上午1:52
 */
public class HasReward implements AccountFilter{

  @Override
  public boolean accept(MkbAccount account) {
    UserInfo userInfo = account.getUserInfo();
    return userInfo != null && userInfo.getNewGoods() != 0;
  }
}
