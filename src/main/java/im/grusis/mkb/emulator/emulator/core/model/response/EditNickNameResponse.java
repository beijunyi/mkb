package im.grusis.mkb.emulator.emulator.core.model.response;

import im.grusis.mkb.emulator.emulator.core.model.basic.GameData;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 下午8:28
 */
public class EditNickNameResponse extends GameData<String> {
  public static final String DuplicateNickNameMessage = "昵称已存在!";
  public static final String TooLongMessage = "昵称只能7位以下哦!";

  public boolean duplicateNickName() {
    String message = getMessage();
    return message != null && message.equals(DuplicateNickNameMessage);
  }

  public boolean tooLong() {
    String message = getMessage();
    return message != null && message.equals(TooLongMessage);
  }
}
