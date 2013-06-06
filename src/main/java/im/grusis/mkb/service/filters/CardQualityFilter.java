package im.grusis.mkb.service.filters;

import im.grusis.mkb.emulator.emulator.core.model.basic.CardGroup;
import im.grusis.mkb.internal.MkbAccount;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午8:59
 */
public class CardQualityFilter implements AccountFilter {

  private int minQuality;
  private int minQuantity;

  public CardQualityFilter(int minQuality, int minQuantity) {
    this.minQuality = minQuality;
    this.minQuantity = minQuantity;
  }

  @Override
  public boolean accept(MkbAccount account) {
    CardGroup cardGroup = account.getCardGroup();
    if(cardGroup == null) {
      return false;
    }
    return true;
  }
}
