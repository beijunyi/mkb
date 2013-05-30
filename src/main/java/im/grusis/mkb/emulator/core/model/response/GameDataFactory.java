package im.grusis.mkb.emulator.core.model.response;

import com.google.gson.Gson;
import im.grusis.mkb.emulator.core.model.basic.GameData;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 上午1:17
 */
public class GameDataFactory {
  public static <T extends GameData> T getGameData(String str, Class<T> clazz) {
    try {
      return new Gson().fromJson(str, clazz);
    } catch(Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
