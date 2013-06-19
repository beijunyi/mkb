package im.grusis.mkb.core.util.dictionary.impl;

import im.grusis.mkb.core.util.dictionary.BasicDict;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午9:53
 */
public class BasicNicknameDict extends BasicDict {

  public final static String CharSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public BasicNicknameDict(String prefix) {
    super(prefix, CharSet, MaxNicknameLength);
    init();
  }
}
