package im.grusis.mkb.core.repository.model;

import im.grusis.mkb.core.emulator.game.model.basic.GoodsList;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午8:07
 */
public class GoodsAssets extends MkbAssets<GoodsList> {

  public static final String AssetName = "goods";

  public GoodsAssets() {
    super(AssetName);
  }
}
