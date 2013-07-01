package im.grusis.mkb.eco.repository;

import im.grusis.mkb.core.repository.MkbRepository;
import im.grusis.mkb.eco.model.Criterion;
import im.grusis.mkb.eco.model.EcoSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 下午10:03
 */
@Repository
public class EcoSettingsRepository extends MkbRepository<EcoSettings> {

  private static final Logger LOG = LoggerFactory.getLogger(EcoSettingsRepository.class);

  public EcoSettingsRepository() {
    super("eco", EcoSettings.class);
  }

  public<T extends EcoSettings> T getSettings(String index, Class<T> clazz) {
    return read(index, clazz);
  }

  public void createOrUpdateSettings(EcoSettings settings) {
    String index = settings.getName();
    if(index == null || index.isEmpty()) {
      LOG.error("Cannot create or update criterion with no name.");
      return;
    }
    write(index, settings, true);
  }

}
