package im.grusis.mkb.emulator.emulator.core.model.basic;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 上午1:06
 */
public class GameData<T>  {

  public static final String BadRequestString = "错误"; // 输入错误,请重新输入!
  public static final String DisconnectedString = "断开";  // 网络链接已断开 请重新登录

  private int status;
  private String message;
  private T data;
  private Version version;

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public Version getVersion() {
    return version;
  }

  public void setVersion(Version version) {
    this.version = version;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean badRequest() {
    return status == 0 || (message != null && message.contains(BadRequestString));
  }

  public boolean disconnected() {
    return message != null && message.contains(DisconnectedString);
  }
}
