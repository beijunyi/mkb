package im.grusis.mkb.eco.model;

public class MapSettings {
  private int targetStage;
  private boolean clearCounterAttack;
  private int specialCardGroup;
  private boolean optimizeCardGroup;
  private int maxTry;

  public MapSettings() {
  }

  public MapSettings(int targetStage) {
    this.targetStage = targetStage;
    clearCounterAttack = true;
    specialCardGroup = 1;
    optimizeCardGroup = false;
    maxTry = 5;
  }

  public int getTargetStage() {
    return targetStage;
  }

  public boolean isClearCounterAttack() {
    return clearCounterAttack;
  }

  public int getSpecialCardGroup() {
    return specialCardGroup;
  }

  public boolean isOptimizeCardGroup() {
    return optimizeCardGroup;
  }

  public int getMaxTry() {
    return maxTry;
  }
}
