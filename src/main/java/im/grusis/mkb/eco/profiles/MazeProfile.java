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

  public static final String Maze = "maze.";
  public static final String Clear = "clear";
  public static final String ClearDefault = "8,7,6,5,4,3,2";
  public static final String ResetBudget = "resetBudget";
  public static final int ResetBudgetDefault = 0;
  public static final String MaxTry = "maxTry";
  public static final int MaxTryDefault = 5;

  private List<Integer> clear;
  private int resetBudget;
  private int maxTry;

  public MazeProfile(Environment environment, String root, MazeProfile defaultProfile) {
    super(environment, root, defaultProfile);
  }

  @Override
  public void read(Environment environment, String root, MazeProfile defaultProfile) {
    String clearValue = environment.getProperty(root + Maze + Clear);
    if(clearValue == null) {
      if(defaultProfile != null) {
        clear = new ArrayList<Integer>(defaultProfile.getClear());
      } else {
        clearValue = ClearDefault;
      }
    }
    if(clear == null) {
      String[] clearArray = clearValue.split(",");
      clear = new ArrayList<Integer>();
      for(String c : clearArray) {
        clear.add(Integer.valueOf(c));
      }
    }
    setResetBudget(environment.getProperty(root + Maze + ResetBudget, Integer.class, defaultProfile == null ? ResetBudgetDefault : defaultProfile.getResetBudget()));
    setMaxTry(environment.getProperty(root + Maze + MaxTry, Integer.class, defaultProfile == null ? MaxTryDefault : defaultProfile.getMaxTry()));
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