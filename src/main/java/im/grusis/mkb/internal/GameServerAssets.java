package im.grusis.mkb.internal;

import java.util.List;

import im.grusis.mkb.emulator.emulator.passport.model.basic.GameServer;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午8:07
 */
public class GameServerAssets extends MkbAssets<List<GameServer>> {

  public static final String AssetName = "servers";

  public GameServerAssets() {
    super(AssetName);
  }
}
