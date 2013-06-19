package im.grusis.mkb.core.repository.model;

import im.grusis.mkb.core.emulator.game.model.basic.Runes;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午8:07
 */
public class RuneAssets extends MkbAssets<Runes> {

  public static final String AssetName = "runes";

  public RuneAssets() {
    super(AssetName);
  }
}
