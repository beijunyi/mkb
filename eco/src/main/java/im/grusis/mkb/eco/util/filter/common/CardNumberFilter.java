package im.grusis.mkb.eco.util.filter.common;

import java.util.List;
import java.util.Map;

import im.grusis.mkb.core.emulator.game.model.basic.UserCardInfo;
import im.grusis.mkb.core.util.AccountFilter;
import im.grusis.mkb.eco.util.filter.util.CardUtils;
import im.grusis.mkb.core.repository.model.MkbAccount;

/**
 * User: Mothership
 * Date: 13-6-15
 * Time: 下午10:59
 */
public class CardNumberFilter implements AccountFilter {

  private CompareOperator compare;
  private List<Map<Integer, Integer>> thresholdMaps;

  public CardNumberFilter(CompareOperator compare, List<Map<Integer, Integer>> thresholdMaps) {
    this.compare = compare;
    this.thresholdMaps = thresholdMaps;
  }

  @Override
  public boolean accept(MkbAccount account) {
    Map<Long, UserCardInfo> cards = account.getUserCards();
    if(cards == null) {
      return false;
    }
    Map<Integer, Integer> cardCount = CardUtils.GetCardCount(cards.values());
    for(Map<Integer, Integer> thresholdMap : thresholdMaps) {
      if(!CardUtils.CompareCardCount(cardCount, thresholdMap, compare)) {
        return false;
      }
    }
    return true;
  }
}
