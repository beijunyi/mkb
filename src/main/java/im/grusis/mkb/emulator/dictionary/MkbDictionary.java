package im.grusis.mkb.emulator.dictionary;

import java.util.Collection;

/**
 * User: beij
 * Date: 31/05/13
 * Time: 10:34
 */
public abstract class MkbDictionary {

  protected Collection<String> usedInstances;

  public abstract String getAlias();

  public abstract boolean hasNext();

  public abstract String next();

  public abstract int tailSize();

  public Collection<String> getUsedInstances() {
    return usedInstances;
  }

  public void setUsedInstances(Collection<String> usedInstances) {
    this.usedInstances = usedInstances;
  }
}
