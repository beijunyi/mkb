package im.grusis.mkb.eco.model;

import java.util.Map;

/**
 * User: Mothership
 * Date: 13-6-27
 * Time: 上午12:32
 */
public class ProductionCriteria extends Criteria {
  private Map<Integer, Integer> cardCount;

  public ProductionCriteria() {
  }

  public ProductionCriteria(Map<Integer, Integer> cardCount) {
    this.cardCount = cardCount;
  }

  public Map<Integer, Integer> getCardCount() {
    return cardCount;
  }

  public void setCardCount(Map<Integer, Integer> cardCount) {
    this.cardCount = cardCount;
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) return true;
    if(!(o instanceof ProductionCriteria)) return false;

    ProductionCriteria that = (ProductionCriteria)o;

    return cardCount.equals(that.cardCount);

  }

  @Override
  public int hashCode() {
    return cardCount.hashCode();
  }
}
