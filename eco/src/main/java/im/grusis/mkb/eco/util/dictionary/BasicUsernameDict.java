package im.grusis.mkb.eco.util.dictionary;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午9:53
 */
public class BasicUsernameDict extends BasicDict {

  public final static String CharSet = "0123456789abcdefghijklmnopqrstuvwxyz";

  public BasicUsernameDict(String prefix) {
    super(prefix, CharSet, MaxUsernameLength);
    init();
  }
}
