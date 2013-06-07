package im.grusis.mkb.emulator.emulator.core.model.basic;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-6-7
 * Time: 下午10:24
 */
public class Battle {
  private String BattleId;
  private int Win;
  private BattleExtData ExtData;
  private String[] prepare;
  private Player AttackPlayer;
  private Player DefendPlayer;
  private List<BattleRound> Battle;

  public String getBattleId() {
    return BattleId;
  }

  public void setBattleId(String battleId) {
    BattleId = battleId;
  }

  public int getWin() {
    return Win;
  }

  public void setWin(int win) {
    Win = win;
  }

  public BattleExtData getExtData() {
    return ExtData;
  }

  public void setExtData(BattleExtData extData) {
    ExtData = extData;
  }

  public String[] getPrepare() {
    return prepare;
  }

  public void setPrepare(String[] prepare) {
    this.prepare = prepare;
  }

  public Player getAttackPlayer() {
    return AttackPlayer;
  }

  public void setAttackPlayer(Player attackPlayer) {
    AttackPlayer = attackPlayer;
  }

  public Player getDefendPlayer() {
    return DefendPlayer;
  }

  public void setDefendPlayer(Player defendPlayer) {
    DefendPlayer = defendPlayer;
  }

  public List<BattleRound> getBattle() {
    return Battle;
  }

  public void setBattle(List<BattleRound> battle) {
    Battle = battle;
  }
}
