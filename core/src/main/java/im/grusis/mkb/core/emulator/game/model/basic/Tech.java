package im.grusis.mkb.core.emulator.game.model.basic;

import java.util.Map;

/**
 * User: Mothership
 * Date: 13-6-12
 * Time: 下午11:22
 */
public class Tech {
  private Map<Integer, LegionTech> Techs;

  public Map<Integer, LegionTech> getTechs() {
    return Techs;
  }

  public void setTechs(Map<Integer, LegionTech> techs) {
    Techs = techs;
  }
}
