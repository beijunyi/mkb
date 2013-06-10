package im.grusis.mkb.emulator.emulator.core.model.response;

import im.grusis.mkb.emulator.emulator.core.model.basic.EnergyUser;

/**
 * User: Mothership
 * Date: 13-6-10
 * Time: 上午12:46
 */
public abstract class EnergyUsingResponse<T extends EnergyUser> extends GameData<T> {
  public static final String NoEnergyString = "行动力不足"; // 行动力不足!每10分钟可恢复1点!您也可以使用晶钻购买行动力哦!

  public boolean noEnergy() {
    return type == 4 || (message != null && message.contains(NoEnergyString));
  }
}
