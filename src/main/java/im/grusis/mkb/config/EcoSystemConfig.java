package im.grusis.mkb.config;

import java.util.*;

import im.grusis.mkb.eco.EcoSystemMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

/**
 * User: Junyi BEI
 * Date: 18/01/2013
 * Time: 22:58
 */
@Configuration
@PropertySource("classpath:/eco.properties")
@ComponentScan(basePackageClasses = EcoSystemMaster.class)
public class EcoSystemConfig {

  private static final String KeepConfigPrefix = "keep.";
  private static final String KeepMin = "min";
  private static final int KeepMinDefault = 4;
  private static final String KeepUpgrade = "upgrade";
  private static final boolean KeepUpgradeDefault = true;

  private static final String StarterConfigPrefix = "starter.";

  private static final String CardConfigPrefix = "card.";
  private static final String UpgradeMaxNumberSuffix = ".upgrade";
  private static final String KeepMaxNumberSuffix = ".keep";

  @Autowired private Environment env;

  @Bean
  public KeepConfig getKeepConfig() {
    int min = env.getProperty(KeepConfigPrefix + KeepMin, Integer.class, KeepMinDefault);
    boolean upgrade = env.getProperty(KeepConfigPrefix + KeepUpgrade, Boolean.class, KeepUpgradeDefault);
    return new KeepConfig(min, upgrade);
  }

  @Bean
  public StarterConfigList getStarterConfig() {
    StarterConfigList starterConfigMap = new StarterConfigList();
    int i = 0;
    String key;
    while(env.containsProperty((key = StarterConfigPrefix + i))) {
      String cards = env.getProperty(key);
      String[] cardArray = cards.split(",");
      starterConfigMap.add(new StarterConfig(cardArray));
      i++;
    }
    return starterConfigMap;
  }

  @Bean
  public CardConfigMap getCardConfigMap() {
    CardConfigMap cardConfigMap = new CardConfigMap();
    int i = 0;
    String key;
    while(env.containsProperty((key = CardConfigPrefix + i))) {
      int id = env.getProperty(key, Integer.class);
      int upgrade = env.getProperty(key + UpgradeMaxNumberSuffix, Integer.class, Integer.MAX_VALUE);
      int keep = env.getProperty(key + KeepMaxNumberSuffix, Integer.class, Integer.MAX_VALUE);
      cardConfigMap.put(id, new CardConfig(id, upgrade, keep));
      i++;
    }
    return cardConfigMap;
  }

  public class KeepConfig {
    int min;
    boolean upgrade;

    public KeepConfig() {
    }

    public KeepConfig(int min, boolean upgrade) {
      this.min = min;
      this.upgrade = upgrade;
    }

    public int getMin() {
      return min;
    }

    public void setMin(int min) {
      this.min = min;
    }

    public boolean isUpgrade() {
      return upgrade;
    }

    public void setUpgrade(boolean upgrade) {
      this.upgrade = upgrade;
    }
  }

  public class StarterConfigList extends ArrayList<StarterConfig> {

  }

  public class StarterConfig {
    private Map<Integer, Integer> starterCards;

    public StarterConfig() {
    }

    public StarterConfig(Map<Integer, Integer> starterCards) {
      this.starterCards = starterCards;
    }

    public StarterConfig(String[] cards) {
      starterCards = new LinkedHashMap<Integer, Integer>();
      for(String card : cards) {
        int cardId = Integer.parseInt(card);
        Integer count = starterCards.get(cardId);
        if(count == null) {
          starterCards.put(cardId, 1);
        } else {
          starterCards.put(cardId, count + 1);
        }
      }
    }

    public Map<Integer, Integer> getStarterCards() {
      return starterCards;
    }

    public void setStarterCards(Map<Integer, Integer> starterCards) {
      this.starterCards = starterCards;
    }
  }

  public class CardConfigMap extends LinkedHashMap<Integer, CardConfig> {

  }

  public class CardConfig {
    private int id;
    private int upgrade;
    private int keep;

    public CardConfig() {
    }

    public CardConfig(int id, int upgrade, int keep) {
      this.id = id;
      this.upgrade = upgrade;
      this.keep = keep;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public int getUpgrade() {
      return upgrade;
    }

    public void setUpgrade(int upgrade) {
      this.upgrade = upgrade;
    }

    public int getKeep() {
      return keep;
    }

    public void setKeep(int keep) {
      this.keep = keep;
    }
  }
}
