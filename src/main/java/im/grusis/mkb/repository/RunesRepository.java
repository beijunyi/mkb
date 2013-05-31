package im.grusis.mkb.repository;

import im.grusis.mkb.emulator.core.model.basic.Rune;
import im.grusis.mkb.emulator.core.model.basic.Runes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 下午10:03
 */
@Repository
public class RunesRepository extends MkbRepository<Rune> {

  private static final Logger Log = LoggerFactory.getLogger(RunesRepository.class);

  public RunesRepository() {
    super("runes", Rune.class);
  }

  public Rune getRune(int runeId) {
    return read(Integer.toString(runeId));
  }

  public void createOrUpdateRune(Rune rune) {
    String index = Integer.toString(rune.getRuneId());
    if(index == null || index.isEmpty()) {
      Log.error("Cannot create or update rune with no rune id.");
      return;
    }
    write(index, rune, true);
  }

  public void saveRunes(Runes runes) {
    for(Rune rune : runes.getRunes()) {
      createOrUpdateRune(rune);
    }
  }

}
