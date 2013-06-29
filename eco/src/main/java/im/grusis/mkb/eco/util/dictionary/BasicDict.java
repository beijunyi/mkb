package im.grusis.mkb.eco.util.dictionary;

import im.grusis.mkb.core.util.MkbDictionary;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午9:15
 */
public class BasicDict implements MkbDictionary {

  protected final char[] charset;
  protected int lengthMax;

  protected String prefix;

  protected int count = 0;
  protected int max;

  public BasicDict(String prefix, String chars, int lengthMax) {
    charset = chars.toCharArray();
    this.prefix = prefix;
    this.lengthMax = lengthMax;
  }


  @Override
  public void init() {
    int flex = MkbDictionary.MaxUsernameLength - prefix.length();
    int charSetSize = charset.length;
    max = 1;
    for(int i = 0; i < flex; i++) {
      max *= charSetSize;
      if(max > Integer.MAX_VALUE / charSetSize) break;
    }
  }

  @Override
  public boolean hasNext() {
    return count < max;
  }

  @Override
  public String next() {
    StringBuilder sb = new StringBuilder();
    int q = count;
    int charSetSize = charset.length;
    int r;
    while(q > 0) {
      r = q % charSetSize;
      sb.insert(0, charset[r]);
      q /= charSetSize;
    }
    count++;
    return prefix + sb.toString();
  }

  public String getPrefix() {
    return prefix;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getCount() {
    return count;
  }
}
