package im.grusis.mkb.eco.model;

public class LegionSettings {
  private int dailyContribution;
  private int benefitedTechnology;

  public LegionSettings() {
    dailyContribution = 50000;
    benefitedTechnology = 1;
  }

  public int getDailyContribution() {
    return dailyContribution;
  }

  public void setDailyContribution(int dailyContribution) {
    this.dailyContribution = dailyContribution;
  }

  public int getBenefitedTechnology() {
    return benefitedTechnology;
  }

  public void setBenefitedTechnology(int benefitedTechnology) {
    this.benefitedTechnology = benefitedTechnology;
  }
}
