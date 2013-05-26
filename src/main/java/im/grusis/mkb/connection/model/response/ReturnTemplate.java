package im.grusis.mkb.connection.model.response;

/**
 * User: Mothership
 * Date: 13-5-24
 * Time: 下午11:17
 */
public class ReturnTemplate<T> {
  private int returnCode;
  private String returnMsg;
  private T returnObjs;

  public int getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(int returnCode) {
    this.returnCode = returnCode;
  }

  public String getReturnMsg() {
    return returnMsg;
  }

  public void setReturnMsg(String returnMsg) {
    this.returnMsg = returnMsg;
  }

  public T getReturnObjs() {
    return returnObjs;
  }

  public void setReturnObjs(T returnObjs) {
    this.returnObjs = returnObjs;
  }
}
