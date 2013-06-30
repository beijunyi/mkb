package im.grusis.mkb.eco.util.filter.common;

import java.util.Map;
import java.util.TreeMap;

import im.grusis.mkb.core.emulator.game.model.basic.UserCard;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.util.AccountFilter;
import im.grusis.mkb.eco.util.filter.util.CardUtils;

/**
 * User: Mothership
 * Date: 13-6-15
 * Time: 下午10:59
 */
public class CardNumberFilter implements AccountFilter {

  private CompareOperator compare;
  private Map<Integer, Integer> thresholdMap;

  public CardNumberFilter(CompareOperator compare, Map<Integer, Integer> thresholdMap) {
    this.compare = compare;
    this.thresholdMap = thresholdMap;
  }

  @Override
  public boolean accept(MkbAccount account) {
    Map<Long, UserCard> cards = account.getUserCards();
    Map<Integer, Integer> cardCount;
    if(cards != null) {
      cardCount = CardUtils.GetCardCount(cards.values());
    } else {
      cardCount = new TreeMap<Integer, Integer>();
    }
    if(account.getNewCards() != null) {
      for(int cardId: account.getNewCards()) {
        Integer count = cardCount.get(cardId);
        if(count == null) {
          cardCount.put(cardId, 1);
        } else {
          cardCount.put(cardId, count + 1);
        }
      }
    }
    return CardUtils.CompareCardCount(cardCount, thresholdMap, compare);
  }
}
