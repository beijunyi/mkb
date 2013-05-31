package im.grusis.mkb.repository;

import im.grusis.mkb.emulator.core.model.basic.AllCard;
import im.grusis.mkb.emulator.core.model.basic.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 下午10:03
 */
@Repository
public class CardsRepository extends MkbRepository<Card> {

  private static final Logger Log = LoggerFactory.getLogger(CardsRepository.class);

  public CardsRepository() {
    super("cards", Card.class);
  }

  public Card getCard(int cardId) {
    return read(Integer.toString(cardId));
  }

  public void createOrUpdateCard(Card card) {
    String index = Integer.toString(card.getCardId());
    if(index == null || index.isEmpty()) {
      Log.error("Cannot create or update card with no card id.");
      return;
    }
    write(index, card, true);
  }

  public void saveCards(AllCard allCard) {
    for(Card card : allCard.getCards()) {
      createOrUpdateCard(card);
    }
  }

}
