package im.grusis.mkb.emulator.emulator.core.model.response;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 下午8:28
 */
public class SendFEnergyResponse extends GameData<String> {

  public static final String EnergySendMaxMessage = "今日赠送达到上限"; // 今日赠送达到上限！

  public boolean energySendMax() {
    return message != null && message.contains(EnergySendMaxMessage);
  }
}
