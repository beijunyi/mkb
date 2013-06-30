package im.grusis.mkb.core.emulator.game.model.response;

import im.grusis.mkb.core.emulator.game.model.basic.UserInfo;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 上午1:15
 */
public class UserGetUserInfoResponse extends GameData<UserInfo> {
  public static final String RequireNickName = "请输入昵称"; // 请输入昵称!

  public boolean isRequireNickName() {
    return message != null && message.contains(RequireNickName);
  }
}
