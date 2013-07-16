package im.grusis.mkb.core.emulator.game.model.basic;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-6-12
 * Time: 下午11:17
 */
public class Members extends MkbObject {
  private List<Member> Members;

  public List<Member> getMembers() {
    return Members;
  }

  public void setMembers(List<Member> members) {
    Members = members;
  }
}
