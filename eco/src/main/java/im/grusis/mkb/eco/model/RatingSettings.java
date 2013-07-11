package im.grusis.mkb.eco.model;

import java.util.Map;
import java.util.TreeMap;

public class RatingSettings extends EcoSettings {

  private Map<Integer, Rating> ratings;

  public RatingSettings(String name) {
    super(name);
  }

  public Map<Integer, Rating> getRatings() {
    if(ratings == null) ratings = new TreeMap<Integer, Rating>();
    return ratings;
  }

}
