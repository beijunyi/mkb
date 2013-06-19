package im.grusis.mkb.core.emulator.web.model.response;

/**
 * User: Mothership
 * Date: 13-5-25
 * Time: 下午5:31
 */
public class RegUserResponse extends StringResponse {

  public static final String DuplicateUserNameMessage = "Game is already activated by this user.";

  public boolean duplicateUsername() {
    String message = getReturnMsg();
    return message != null && message.equals(DuplicateUserNameMessage);
  }
}
