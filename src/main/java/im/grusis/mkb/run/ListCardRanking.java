package im.grusis.mkb.run;

import java.util.*;

import im.grusis.mkb.config.RuntimeConfig;
import im.grusis.mkb.eco.EcoMind;
import im.grusis.mkb.emulator.emulator.MkbEmulator;
import im.grusis.mkb.emulator.emulator.core.model.basic.Card;
import im.grusis.mkb.emulator.emulator.core.model.basic.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * User: Mothership
 * Date: 13-6-17
 * Time: 下午10:14
 */
public class ListCardRanking {

  private static final Logger Log = LoggerFactory.getLogger(ListCardRanking.class);

  public static void main(String[] args) throws Exception{
    String username = "tug100";
    int level = 10;
    boolean detail = true;
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(RuntimeConfig.class);
    ctx.start();

    MkbEmulator emulator = ctx.getBean(MkbEmulator.class);
    EcoMind ecoMind = ctx.getBean(EcoMind.class);


    final Map<Card, Double> scores = new HashMap<Card, Double>();
    Map<Integer, Card> cards = emulator.gameGetCards(username, false);
    Map<Integer, Skill> skills = emulator.gameGetSkills(username, false);
//    ecoMind.getSkillScore("299", skills);

    for(Card card : cards.values()) {
      scores.put(card, ecoMind.getCardScore(card, level, skills));
    }
    List<Card> cardRanking = new ArrayList<Card>(scores.keySet());
    Collections.sort(cardRanking, new Comparator<Card>() {
      @Override
      public int compare(Card o1, Card o2) {
        return scores.get(o2).compareTo(scores.get(o1));
      }
    });

    int rank = 1;
    for(Card c : cardRanking) {
      if(!detail) {
        Log.info("{} {}\t{}\t{}\t{}", rank++, c.getCardName(), scores.get(c).intValue(), c.getAttackArray()[level], c.getHpArray()[level]);
      } else {
        Log.info("{} {}\t{}\t{}", rank++, c.getCardName(), scores.get(c).intValue(), ecoMind.getDetailCardScore(c, level, skills));
      }
    }
  }
}
