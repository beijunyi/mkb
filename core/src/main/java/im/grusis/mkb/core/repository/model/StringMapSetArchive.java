package im.grusis.mkb.core.repository.model;

import java.util.Map;
import java.util.Set;

/**
 * User: beij
 * Date: 31/05/13
 * Time: 19:01
 */
public class StringMapSetArchive extends MkbArchive {

  private Map<String, Set<String>> mapSet;

  public StringMapSetArchive() {
  }

  public StringMapSetArchive(String archiveName, Map<String, Set<String>> mapSet) {
    super(archiveName);
    this.mapSet = mapSet;
  }

  public Map<String, Set<String>> getMapSet() {
    return mapSet;
  }

  public void setMapSet(Map<String, Set<String>> mapSet) {
    this.mapSet = mapSet;
  }
}
