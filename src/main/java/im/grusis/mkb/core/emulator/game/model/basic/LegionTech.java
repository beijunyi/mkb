package im.grusis.mkb.core.emulator.game.model.basic;

/**
 * User: Mothership
 * Date: 13-6-12
 * Time: 下午11:20
 */
public class LegionTech {
  private String Name;
  private String Description;
  private long Contribute;
  private int Level;
  private String NextContribute;
  private String CurrContribute;

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getDescription() {
    return Description;
  }

  public void setDescription(String description) {
    Description = description;
  }

  public long getContribute() {
    return Contribute;
  }

  public void setContribute(long contribute) {
    Contribute = contribute;
  }

  public int getLevel() {
    return Level;
  }

  public void setLevel(int level) {
    Level = level;
  }

  public String getNextContribute() {
    return NextContribute;
  }

  public void setNextContribute(String nextContribute) {
    NextContribute = nextContribute;
  }

  public String getCurrContribute() {
    return CurrContribute;
  }

  public void setCurrContribute(String currContribute) {
    CurrContribute = currContribute;
  }
}
