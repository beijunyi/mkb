package im.grusis.mkb.eco.profiles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.env.Environment;

/**
 * User: Mothership
 * Date: 13-6-13
 * Time: 下午11:08
 */
public class MazeProfile extends Profile<MazeProfile> {

  public static final String Prefix = "maze.";
  public static final String Clear = "clear";
  public static final String ResetBudget = "resetBudget";
  public static final String MaxTry = "maxTry";

  private List<Integer> clear;
  private int resetBudget;
  private int maxTry;

  public MazeProfile(Environment environment, String root, MazeProfile defaultProfile) {
    super(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, MazeProfile defaultProfile) {
    String clearValue = environment.getProperty(root + Prefix + Clear);
    if(clearValue == null) {
      clear = new ArrayList<Integer>(defaultProfile.getClear());
    } else {
      String[] clearArray = clearValue.split(",");
      clear = new ArrayList<Integer>();
      for(String c : clearArray) {
        clear.add(Integer.valueOf(c));
      }
    }
    setResetBudget(environment.getProperty(root + Prefix + ResetBudget, Integer.class, defaultProfile == null ? null : defaultProfile.getResetBudget()));
    setMaxTry(environment.getProperty(root + Prefix + MaxTry, Integer.class, defaultProfile == null ? null : defaultProfile.getMaxTry()));
  }

  public List<Integer> getClear() {
    return clear;
  }

  public void setClear(List<Integer> clear) {
    this.clear = clear;
  }

  public int getResetBudget() {
    return resetBudget;
  }

  public void setResetBudget(int resetBudget) {
    this.resetBudget = resetBudget;
  }

  public int getMaxTry() {
    return maxTry;
  }

  public void setMaxTry(int maxTry) {
    this.maxTry = maxTry;
  }
}