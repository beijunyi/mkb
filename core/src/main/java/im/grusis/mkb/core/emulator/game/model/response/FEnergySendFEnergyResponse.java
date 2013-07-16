package im.grusis.mkb.core.emulator.game.model.response;

import im.grusis.mkb.core.emulator.game.model.basic.MkbObject;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 下午8:28
 */
public class FEnergySendFEnergyResponse extends GameData<MkbObject> {

  public static final String EnergySendMaxMessage = "今日赠送达到上限"; // 今日赠送达到上限！
  public static final String EnergyAlreadySentMessage = "今日已经赠送过该好友"; // 今日已经赠送过该好友！

  public boolean energySendMax() {
    return message != null && message.contains(EnergySendMaxMessage);
  }

  public boolean energyAlreadySent() {
    return message != null && message.contains(EnergyAlreadySentMessage);
  }
}
