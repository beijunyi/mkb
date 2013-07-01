package im.grusis.mkb.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import im.grusis.mkb.core.config.MkbCoreConfig;
import im.grusis.mkb.core.emulator.MkbEmulator;
import im.grusis.mkb.core.emulator.game.model.basic.CardDef;
import im.grusis.mkb.core.emulator.game.model.basic.UserCard;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * User: Mothership
 * Date: 13-6-30
 * Time: 下午10:43
 */
public class FindCard {

  public static void main(String args[]) throws Exception {
    String username = "tug100";
    String cardName = "光明之龙";

    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MkbCoreConfig.class);
    ctx.start();
    List<Long> userCardIds = new ArrayList<Long>();
    MkbEmulator emulator = ctx.getBean(MkbEmulator.class);

    Map<Long, UserCard> userCards = emulator.card().getUserCards(username, false);
    Map<Integer, CardDef> cardDefs = emulator.card().getAllCard(username, false);

    for(UserCard card : userCards.values()) {
      CardDef def = cardDefs.get(card.getCardId());
      if(def.getCardName().contains(cardName)) {
        userCardIds.add(card.getUserCardId());
      }
    }

    for(long userCardId : userCardIds) {
      System.out.print(userCardId + "_");
    }

  }
}
