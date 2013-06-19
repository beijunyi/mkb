package im.grusis.mkb.eco.configs;

import java.util.List;
import java.util.Map;

import im.grusis.mkb.core.util.CollectionUtil;

/**
 * User: Mothership
 * Date: 13-6-15
 * Time: 下午8:16
 */
public class BeginnerConfig {

  public static final String Beginner = "beginner.";

  private Map<Integer, Integer> starterCards;

  public BeginnerConfig() {
  }

  public BeginnerConfig(Map<Integer, Integer> starterCards) {
    this.starterCards = starterCards;
  }

  public BeginnerConfig(List<Integer> cards) {
    this(CollectionUtil.instanceCount(cards));
  }

  public Map<Integer, Integer> getStarterCards() {
    return starterCards;
  }
}