package im.grusis.mkb.core.emulator.game.model.basic;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-5-29
 * Time: 下午11:15
 */
public class Runes extends MkbObject {
  private List<RuneDef> Runes;

  public List<RuneDef> getRunes() {
    return Runes;
  }

  public void setRunes(List<RuneDef> runes) {
    Runes = runes;
  }
}
