package im.grusis.mkb.internal;

import java.util.Set;

/**
 * User: beij
 * Date: 31/05/13
 * Time: 19:01
 */
public class StringSetArchive extends MkbArchive {

  private Set<String> set;

  public StringSetArchive() {
  }

  public StringSetArchive(String archiveName, Set<String> set) {
    super(archiveName);
    this.set = set;
  }

  public Set<String> getSet() {
    return set;
  }

  public void setSet(Set<String> set) {
    this.set = set;
  }
}
