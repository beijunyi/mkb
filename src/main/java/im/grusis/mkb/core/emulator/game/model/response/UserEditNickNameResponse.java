package im.grusis.mkb.core.emulator.game.model.response;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 下午8:28
 */
public class UserEditNickNameResponse extends GameData<String> {
  public static final String DuplicateNickNameMessage = "昵称已存在!";
  public static final String TooLongMessage = "昵称只能7位以下哦!";

  public boolean duplicateNickName() {
    return message != null && message.equals(DuplicateNickNameMessage);
  }

  public boolean tooLong() {
    return message != null && message.equals(TooLongMessage);
  }
}
