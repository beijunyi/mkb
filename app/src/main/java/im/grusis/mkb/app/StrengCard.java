package im.grusis.mkb.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import im.grusis.mkb.core.config.MkbCoreConfig;
import im.grusis.mkb.core.emulator.AutomatedServiceEngine;
import im.grusis.mkb.core.emulator.MkbEmulator;
import im.grusis.mkb.core.emulator.game.model.basic.Card;
import im.grusis.mkb.core.emulator.game.model.basic.UserCard;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * User: Mothership
 * Date: 13-6-20
 * Time: 下午11:23
 */
public class StrengCard {
  public static void main(String[] args) throws Exception{
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MkbCoreConfig.class);
    ctx.start();

    String username = "tug100";
    String cardName = "堕落精灵";
    int cardLevel = 12;
    int targetLevel = 15;

    AutomatedServiceEngine ase = ctx.getBean(AutomatedServiceEngine.class);
    MkbEmulator emulator = ctx.getBean(MkbEmulator.class);

    Card card = null;
    Map<Integer, Card> allCard = emulator.gameGetCards(username, true);
    for(Card c : allCard.values()) {
      if(c.getCardName().contains(cardName)) {
        card = c;
        break;
      }
    }
    if(card == null) {
      return;
    }
    UserCard uc = null;
    Map<Long, UserCard> userCards = emulator.gameGetUserCards(username, true);
    for(UserCard userCard : userCards.values()) {
      if(userCard.getCardId() == card.getCardId() && userCard.getLevel() == cardLevel) {
        uc = userCard;
        break;
      }
    }
    if(uc == null) {
      return;
    }

    long expNeeded = card.getExpArray()[targetLevel] - uc.getExp();
    long exp = 0;
    List<Long> resources = new ArrayList<Long>();
    for(UserCard userCard : userCards.values()) {
      Card c = allCard.get(userCard.getCardId());
      if(c.getColor() == 1) {
        resources.add(userCard.getUserCardId());
        exp += c.getCost() * 7 + 15;
      }
      if(c.getColor() == 2) {
        resources.add(userCard.getUserCardId());
        exp += c.getCost() * 8 + 27;
        if(c.getCost() == 7) exp++;
      }
      if(exp >= expNeeded) {
        break;
      }
    }

    while(resources.size() != 0) {
      int size = 30;
      if(size > resources.size()) {
        size = resources.size();
      }
      List<Long> sub = resources.subList(0, size);
      emulator.gameUpgradeCard(username, uc.getUserCardId(), sub);
      resources.removeAll(sub);
    }




  }
}
