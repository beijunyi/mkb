package im.grusis.mkb.core.repository.model;

import im.grusis.mkb.core.emulator.game.model.basic.MapStageAll;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午8:07
 */
public class MapStageAssets extends MkbAssets<MapStageAll> {

  public static final String AssetName = "maps";

  public MapStageAssets() {
    super(AssetName);
  }
}
