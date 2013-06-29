package im.grusis.mkb.eco.repository;

import im.grusis.mkb.core.repository.MkbRepository;
import im.grusis.mkb.eco.util.dictionary.DictRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * User: Mothership
 * Date: 13-6-29
 * Time: 下午6:03
 */
@Repository
public class DictionaryRepository extends MkbRepository<DictRecord> {
  private static final Logger LOG = LoggerFactory.getLogger(DictionaryRepository.class);

  public DictionaryRepository() {
    super("dict", DictRecord.class);
  }

  public<T extends DictRecord> T getDictRecord(String index, Class<T> clazz) {
    return read(index, clazz);
  }

  public void createOrUpdateRecord(String index, DictRecord dictRecord) {
    if(index == null || index.isEmpty()) {
      LOG.error("Cannot create or update criterion with no name.");
      return;
    }
    write(index, dictRecord, true);
  }
}
