package im.grusis.mkb.emulator.emulator.core.model.basic;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-6-16
 * Time: 下午2:19
 */
public class LegionAttackInfo {

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
