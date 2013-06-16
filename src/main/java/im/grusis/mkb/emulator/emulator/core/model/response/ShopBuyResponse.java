package im.grusis.mkb.emulator.emulator.core.model.response;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 上午1:15
 */
public class ShopBuyResponse extends GameData<Integer> {
  public static final String NoCurrencyMessage = "没有";  // 您没有剩余的魔幻券哦!

  public boolean noCurrency() {
    return message != null && message.contains(NoCurrencyMessage);
  }
}
