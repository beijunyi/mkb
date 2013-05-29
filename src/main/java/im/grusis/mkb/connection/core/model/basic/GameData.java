package im.grusis.mkb.connection.core.model.basic;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 上午1:06
 */
public class GameData<T>  {
  private int status;
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
}
