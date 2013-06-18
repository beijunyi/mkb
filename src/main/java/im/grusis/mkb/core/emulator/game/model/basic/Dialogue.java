package im.grusis.mkb.core.emulator.game.model.basic;

/**
 * User: Mothership
 * Date: 13-6-7
 * Time: 下午9:48
 */
public class Dialogue {
  private int StoryId;
  private String Did;
  private String NPC;
  private String Dialogue;
  private String Icon;
  private int Orientations;
  private int Opportunity;

  public int getStoryId() {
    return StoryId;
  }

  public void setStoryId(int storyId) {
    StoryId = storyId;
  }

  public String getDid() {
    return Did;
  }

  public void setDid(String did) {
    Did = did;
  }

  public String getNPC() {
    return NPC;
  }

  public void setNPC(String NPC) {
    this.NPC = NPC;
  }

  public String getDialogue() {
    return Dialogue;
  }

  public void setDialogue(String dialogue) {
    Dialogue = dialogue;
  }

  public String getIcon() {
    return Icon;
  }

  public void setIcon(String icon) {
    Icon = icon;
  }

  public int getOrientations() {
    return Orientations;
  }

  public void setOrientations(int orientations) {
    Orientations = orientations;
  }

  public int getOpportunity() {
    return Opportunity;
  }

  public void setOpportunity(int opportunity) {
    Opportunity = opportunity;
  }
}
