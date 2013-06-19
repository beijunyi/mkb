package im.grusis.mkb.core.repository.model;

import im.grusis.mkb.core.emulator.game.model.basic.AllCard;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午8:07
 */
public class CardAssets extends MkbAssets<AllCard> {
  public static final String AssetName = "cards";


  public CardAssets() {
    super(AssetName);
  }
}
