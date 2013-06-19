package im.grusis.mkb.eco.util.dictionary;

/**
 * User: beij
 * Date: 31/05/13
 * Time: 10:34
 */
public abstract class MkbDictionary {

  public static final int MaxNicknameLength = 7;
  public static final int MaxUsernameLength = 15;

  public abstract void init();

  public abstract boolean hasNext();

  public abstract String next();

}
