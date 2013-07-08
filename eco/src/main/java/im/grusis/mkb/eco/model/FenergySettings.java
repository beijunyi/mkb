package im.grusis.mkb.eco.model;

public class FenergySettings {
  private boolean preferLegionMember;
  private boolean preferHighRankPlayer;

  public FenergySettings() {
    preferLegionMember = true;
    preferHighRankPlayer = true;
  }

  public boolean isPreferLegionMember() {
    return preferLegionMember;
  }

  public void setPreferLegionMember(boolean preferLegionMember) {
    this.preferLegionMember = preferLegionMember;
  }

  public boolean isPreferHighRankPlayer() {
    return preferHighRankPlayer;
  }

  public void setPreferHighRankPlayer(boolean preferHighRankPlayer) {
    this.preferHighRankPlayer = preferHighRankPlayer;
  }
}
