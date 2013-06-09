package im.grusis.mkb.emulator.emulator.core.model.basic;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 上午1:06
 */
public class GameData<T>  {

  public static final String BadRequestString = "错误"; // 输入错误,请重新输入!
  public static final String DisconnectedString = "断开";  // 网络链接已断开 请重新登录
  public static final String NoEnergyString = "行动力不足"; // 行动力不足!每10分钟可恢复1点!您也可以使用晶钻购买行动力哦!

  private int status;
  private String message;
  private T data;
  private int type;
  private Version version;
  private List<AchievementInfo> AchievementInfos;

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

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public List<AchievementInfo> getAchievementInfos() {
    return AchievementInfos;
  }

  public void setAchievementInfos(List<AchievementInfo> achievementInfos) {
    AchievementInfos = achievementInfos;
  }

  public boolean badRequest() {
    return status == 0 || (message != null && message.contains(BadRequestString));
  }

  public boolean disconnected() {
    return message != null && message.contains(DisconnectedString);
  }

  public boolean noEnergy() {
    return type == 4 || (message != null && message.contains(NoEnergyString));
  }
}
