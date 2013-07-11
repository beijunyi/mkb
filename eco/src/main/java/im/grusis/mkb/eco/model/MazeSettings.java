package im.grusis.mkb.eco.model;

import java.util.*;

public class MazeSettings {
  private List<Integer> clearOrder;
  private int specialCardGroup;
  private Map<Integer, Integer> resetBudgets;
  private int maxTry;

  public MazeSettings() {
  }

  public MazeSettings(int maxMaze) {
    clearOrder = new ArrayList<Integer>();
    specialCardGroup = 1;
    resetBudgets = new TreeMap<Integer, Integer>();
    for(int i = maxMaze; i > 1; i--) {
      clearOrder.add(i);
      resetBudgets.put(i, 0);
    }
    maxTry = 5;
  }

  public List<Integer> getClearOrder() {
    return clearOrder;
  }

  public void setClearOrder(List<Integer> clearOrder) {
    this.clearOrder = clearOrder;
  }

  public Map<Integer, Integer> getResetBudgets() {
    return resetBudgets;
  }

  public void setResetBudgets(Map<Integer, Integer> resetBudgets) {
    this.resetBudgets = resetBudgets;
  }

  public int getMaxTry() {
    return maxTry;
  }

  public void setMaxTry(int maxTry) {
    this.maxTry = maxTry;
  }

  public int getSpecialCardGroup() {
    return specialCardGroup;
  }

  public void setSpecialCardGroup(int specialCardGroup) {
    this.specialCardGroup = specialCardGroup;
  }
}
