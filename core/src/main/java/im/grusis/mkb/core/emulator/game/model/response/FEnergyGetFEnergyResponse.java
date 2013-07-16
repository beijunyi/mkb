package im.grusis.mkb.core.emulator.game.model.response;

import im.grusis.mkb.core.emulator.game.model.basic.MkbObject;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 下午8:28
 */
public class FEnergyGetFEnergyResponse extends GameData<MkbObject> {

  public static final String EnergyGetMaxMessage = "今日领取达到上限"; // 今日领取达到上限！
  public static final String EnergyOverMaxMessage = "您的行动力目前达到或超过50点"; // 您的行动力目前达到或超过50点，请使用后再接受！

  public boolean energyGetMax() {
    return message != null && message.contains(EnergyGetMaxMessage);
  }

  public boolean energyOverMax() {
    return message != null && message.contains(EnergyOverMaxMessage);
  }
}
