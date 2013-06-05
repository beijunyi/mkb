package im.grusis.mkb.emulator.dictionary;

/**
 * User: beij
 * Date: 31/05/13
 * Time: 10:34
 */
public abstract class MkbDictionary {

  public static final int MaxNicknameLength = 7;
  public static final int MaxUsernameLength = 10;

  public abstract void init();

  public abstract boolean hasNext();

  public abstract String next();

  public abstract int tailSize();

}
