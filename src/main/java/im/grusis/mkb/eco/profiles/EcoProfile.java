package im.grusis.mkb.eco.profiles;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:12
 */
public class EcoProfile<T extends EcoProfile> extends Profile<T> {
  private BossProfile bossProfile;
  private CardGroupProfile cardGroupProfile;
  private CardProfile cardProfile;
  private ChipProfile chipProfile;
  private EnergyProfile energyProfile;
  private LegionProfile legionProfile;
  private MazeProfile mazeProfile;
  private RuneProfile runeProfile;
  private StageProfile stageProfile;

  public EcoProfile(Environment environment, String root, T defaultProfile) {
    super(environment, root, defaultProfile);
  }

  public void read(Environment environment, String root, EcoProfile defaultProfile) {
    bossProfile = new BossProfile(environment, root, defaultProfile == null ? null : defaultProfile.getBossProfile());
    cardGroupProfile = new CardGroupProfile(environment, root, defaultProfile == null ? null : defaultProfile.getCardGroupProfile());
    chipProfile = new ChipProfile(environment, root, defaultProfile == null ? null : defaultProfile.getChipProfile());
    energyProfile = new EnergyProfile(environment, root, defaultProfile == null ? null : defaultProfile.getEnergyProfile());
    legionProfile = new LegionProfile(environment, root, defaultProfile == null ? null : defaultProfile.getLegionProfile());
    mazeProfile = new MazeProfile(environment, root, defaultProfile == null ? null : defaultProfile.getMazeProfile());
    runeProfile = new RuneProfile(environment, root, defaultProfile == null ? null : defaultProfile.getRuneProfile());
    stageProfile = new StageProfile(environment, root, defaultProfile == null ? null : defaultProfile.getStageProfile());
  }

  public BossProfile getBossProfile() {
    return bossProfile;
  }

  public void setBossProfile(BossProfile bossProfile) {
    this.bossProfile = bossProfile;
  }

  public CardGroupProfile getCardGroupProfile() {
    return cardGroupProfile;
  }

  public void setCardGroupProfile(CardGroupProfile cardGroupProfile) {
    this.cardGroupProfile = cardGroupProfile;
  }

  public CardProfile getCardProfile() {
    return cardProfile;
  }

  public void setCardProfile(CardProfile cardProfile) {
    this.cardProfile = cardProfile;
  }

  public ChipProfile getChipProfile() {
    return chipProfile;
  }

  public void setChipProfile(ChipProfile chipProfile) {
    this.chipProfile = chipProfile;
  }

  public EnergyProfile getEnergyProfile() {
    return energyProfile;
  }

  public void setEnergyProfile(EnergyProfile energyProfile) {
    this.energyProfile = energyProfile;
  }

  public LegionProfile getLegionProfile() {
    return legionProfile;
  }

  public void setLegionProfile(LegionProfile legionProfile) {
    this.legionProfile = legionProfile;
  }

  public MazeProfile getMazeProfile() {
    return mazeProfile;
  }

  public void setMazeProfile(MazeProfile mazeProfile) {
    this.mazeProfile = mazeProfile;
  }

  public RuneProfile getRuneProfile() {
    return runeProfile;
  }

  public void setRuneProfile(RuneProfile runeProfile) {
    this.runeProfile = runeProfile;
  }

  public StageProfile getStageProfile() {
    return stageProfile;
  }

  public void setStageProfile(StageProfile stageProfile) {
    this.stageProfile = stageProfile;
  }
}
