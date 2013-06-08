package im.grusis.mkb.internal.accountFilter;

import im.grusis.mkb.emulator.emulator.core.model.basic.CardGroup;
import im.grusis.mkb.internal.MkbAccount;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午8:59
 */
public class HasCardFilter implements AccountFilter {

  private int cardId;
  private int minQuantity;

  public HasCardFilter(int cardId, int minQuantity) {
    this.cardId = cardId;
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
