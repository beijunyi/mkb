package im.grusis.mkb.core.emulator.game.model.basic;

import java.util.LinkedHashMap;

/**
 * User: Mothership
 * Date: 13-6-9
 * Time: 下午4:14
 */
public class UserChip extends LinkedHashMap<Integer, Chip> {
  public void addChip(int chipId) {
    Chip chip = get(chipId);
    if(chip != null) {
      chip.setNum(chip.getNum() + 1);
    }
  }
}
