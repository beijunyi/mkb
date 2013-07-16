package im.grusis.mkb.core.emulator.game.model.basic;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-6-16
 * Time: 下午2:19
 */
public class LegionAttackInfo extends MkbObject {

  private LegionAttackUInfo uinfo;
  private List<LegionAttackDetail> info;
  private List<LegionAttackNext> next;

  public LegionAttackUInfo getUinfo() {
    return uinfo;
  }

  public List<LegionAttackDetail> getInfo() {
    return info;
  }

  public List<LegionAttackNext> getNext() {
    return next;
  }
}
