package im.grusis.mkb.repository;

import im.grusis.mkb.internal.MkbAssets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 下午10:03
 */
@Repository
public class AssetsRepository extends MkbRepository<MkbAssets> {

  private static final Logger Log = LoggerFactory.getLogger(AssetsRepository.class);

  public AssetsRepository() {
    super("assets", MkbAssets.class);
  }

  public<T extends MkbAssets> T getAssets(String name, Class<T> clazz) {
    return read(name, clazz);
  }

  public<T extends MkbAssets> void createOrUpdateAssets(T assets) {
    String name = assets.getAssetName();
    if(name == null || name.isEmpty()) {
      Log.error("Cannot create or update assets with no name.");
      return;
    }
    write(name, assets, true);
  }

}
