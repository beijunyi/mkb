package im.grusis.mkb.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import im.grusis.mkb.core.config.MkbCoreConfig;
import im.grusis.mkb.core.emulator.AutomatedServiceEngine;
import im.grusis.mkb.core.emulator.EmulatorCard;
import im.grusis.mkb.core.emulator.EmulatorStreng;
import im.grusis.mkb.core.emulator.game.model.basic.CardDef;
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

    String username = "urnmso90377";
    String cardName = "机械飞龙";
    int cardLevel = 10;
    int targetLevel = 15;

    AutomatedServiceEngine ase = ctx.getBean(AutomatedServiceEngine.class);
    EmulatorCard card = ctx.getBean(EmulatorCard.class);
    EmulatorStreng streng = ctx.getBean(EmulatorStreng.class);

    CardDef cardDef = null;
    Map<Integer, CardDef> allCard = card.getAllCard(username, true);
    for(CardDef c : allCard.values()) {
      if(c.getCardName().contains(cardName)) {
        cardDef = c;
        break;
      }
    }
    if(cardDef == null) {
      return;
    }
    UserCard uc = null;
    Map<Long, UserCard> userCards = card.getUserCards(username, true);
    for(UserCard userCard : userCards.values()) {
      if(userCard.getCardId() == cardDef.getCardId() && userCard.getLevel() == cardLevel) {
        uc = userCard;
        break;
      }
    }
    if(uc == null) {
      return;
    }

    long expNeeded = cardDef.getExpArray()[targetLevel] - uc.getExp();
    long exp = 0;
    List<Long> resources = new ArrayList<Long>();
    for(UserCard userCard : userCards.values()) {
      CardDef c = allCard.get(userCard.getCardId());
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
      streng.card(username, uc.getUserCardId(), sub);
      resources.removeAll(sub);
    }




  }
}
