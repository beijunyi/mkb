package im.grusis.mkb.connection.model;

import com.google.gson.Gson;
import im.grusis.mkb.connection.model.response.ResponseTemplate;

/**
 * User: Mothership
 * Date: 13-5-25
 * Time: 下午5:21
 */
public class ResponseFactory {

  public static <T> T getResponse(String str, String key, Class<T> clazz) {
    try {
      String result = new ResponseTemplate(str, key).getResult();
      return new Gson().fromJson(result, clazz);
    } catch(Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
