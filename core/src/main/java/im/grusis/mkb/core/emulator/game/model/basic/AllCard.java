package im.grusis.mkb.core.emulator.game.model.basic;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-5-29
 * Time: 下午11:15
 */
public class AllCard extends MkbObject {
  private List<CardDef> Cards;

  public List<CardDef> getCards() {
    return Cards;
  }

  public void setCards(List<CardDef> cards) {
    Cards = cards;
  }
}
