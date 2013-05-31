package im.grusis.mkb.repository;

import java.util.List;

import im.grusis.mkb.emulator.emulator.core.model.basic.AllSkill;
import im.grusis.mkb.emulator.emulator.core.model.basic.Skill;
import im.grusis.mkb.internal.StringList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 下午10:03
 */
@Repository
public class DictionaryRepository extends MkbRepository<StringList> {

  private static final Logger Log = LoggerFactory.getLogger(DictionaryRepository.class);

  public DictionaryRepository() {
    super("dicts", StringList.class);
  }

  public StringList getStringlist(String alias) {
    return read(alias);
  }

  public void createOrUpdateStringList(StringList list, String alias) {
    if(alias == null || alias.isEmpty()) {
      Log.error("Cannot create or update string list with no alias.");
      return;
    }
    write(alias, list, true);
  }

}
