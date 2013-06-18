package im.grusis.mkb.core.repository.model;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 下午11:47
 */
public class MkbArchive {
  private String archiveName;

  public MkbArchive() {
  }

  public MkbArchive(String archiveName) {
    this.archiveName = archiveName;
  }

  public String getArchiveName() {
    return archiveName;
  }

  public void setArchiveName(String archiveName) {
    this.archiveName = archiveName;
  }
}
