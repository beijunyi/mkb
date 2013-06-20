package im.grusis.mkb.eco.heuristics;

/**
 * User: Mothership
 * Date: 13-6-15
 * Time: 下午8:19
 */
public class SystemConfig {

  public static final String SystemConfigPrefix = "system.";
  public static final String Size = "size";

  private int size;

  public SystemConfig(int size) {
    this.size = size;
  }

  public int getSize() {
    return size;
  }
}
