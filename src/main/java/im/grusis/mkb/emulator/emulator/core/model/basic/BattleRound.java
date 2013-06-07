package im.grusis.mkb.emulator.emulator.core.model.basic;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-6-7
 * Time: 下午10:36
 */
public class BattleRound {
  private int Round;
  private boolean isAttack;
  private List<Opp> Opps;

  public class Opp {
    private String UUID;
    private int Opp;
    private String[] Target;
    private int Value;
  }
}
