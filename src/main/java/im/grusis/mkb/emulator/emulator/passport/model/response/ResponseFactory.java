package im.grusis.mkb.emulator.emulator.passport.model.response;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Mothership
 * Date: 13-5-25
 * Time: 下午5:21
 */
public class ResponseFactory {

  private static final Logger Log = LoggerFactory.getLogger(ResponseFactory.class);

  public static <T extends ReturnTemplate> T getResponse(String str, String key, Class<T> clazz) {
    try {
      String result = new ResponseTemplate(str, key).getResult();
      Log.info("Parsing response string {}\ninto {}", result, clazz.getSimpleName());
      return new Gson().fromJson(result, clazz);
    } catch(Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
