package im.grusis.mkb.core.emulator.game.model.response;

import im.grusis.mkb.core.emulator.game.model.basic.MkbObject;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 上午1:15
 */
public class ShopBuyResponse extends GameData<String> {
  public static final String NoCurrencyMessage = "没有";  // 您没有剩余的魔幻券哦!

  public boolean noCurrency() {
    return message != null && message.contains(NoCurrencyMessage);
  }
}
