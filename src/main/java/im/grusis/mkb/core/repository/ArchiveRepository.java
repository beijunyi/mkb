package im.grusis.mkb.core.repository;

import im.grusis.mkb.core.repository.model.MkbArchive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 下午10:03
 */
@Repository
public class ArchiveRepository extends MkbRepository<MkbArchive> {

  private static final Logger Log = LoggerFactory.getLogger(ArchiveRepository.class);

  public ArchiveRepository() {
    super("archive", MkbArchive.class);
  }

  public<T extends MkbArchive> T getArchive(String name, Class<T> clazz) {
    return read(name, clazz);
  }

  public<T extends MkbArchive> void createOrUpdateArchive(T archive) {
    String name = archive.getArchiveName();
    if(name == null || name.isEmpty()) {
      Log.error("Cannot create or update archive with no name.");
      return;
    }
    write(name, archive, true);
  }

}
