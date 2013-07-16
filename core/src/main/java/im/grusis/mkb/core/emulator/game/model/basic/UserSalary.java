package im.grusis.mkb.core.emulator.game.model.basic;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-6-16
 * Time: 下午1:11
 */
public class UserSalary extends MkbObject {
  private List<SalaryInfo> SalaryInfos;

  public List<SalaryInfo> getSalaryInfos() {
    return SalaryInfos;
  }
}
