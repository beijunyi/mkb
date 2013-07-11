package im.grusis.mkb.eco.model;

import java.util.Map;
import java.util.TreeMap;

public class CardRatingSettings extends EcoSettings {

  private Map<Integer, CardRating> cardRatings;

  public CardRatingSettings(String name) {
    super(name);
  }

  public Map<Integer, CardRating> getCardRatings() {
    if(cardRatings == null) cardRatings = new TreeMap<Integer, CardRating>();
    return cardRatings;
  }

  public void setCardRatings(Map<Integer, CardRating> cardRatings) {
    this.cardRatings = cardRatings;
  }

}
