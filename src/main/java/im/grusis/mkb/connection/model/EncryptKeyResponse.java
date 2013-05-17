package im.grusis.mkb.connection.model;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: beij
 * Date: 17/05/13
 * Time: 14:48
 */
public class EncryptKeyResponse {
  public static final Pattern KeyPattern = Pattern.compile("muhe_encrypt=\"([^\"]*)\";");
  public static final Pattern UrlPattern = Pattern.compile("muhe_url=\"([^\"]*)\";");

  private String key;
  private String url;
  private Map<String, String> extraProperties = new HashMap<String, String>();

  public EncryptKeyResponse(String responseString) {
    Matcher keyMatcher = KeyPattern.matcher(responseString);
    if(keyMatcher.find()) key = keyMatcher.group(1);
    Matcher urlMatcher = UrlPattern.matcher(responseString);
    if(urlMatcher.find()) url = urlMatcher.group(1);
    if(url.contains(";")) {
      int urlBreakIndex = url.indexOf(";");
      String tail = url.substring(urlBreakIndex);
      url = url.substring(0, urlBreakIndex);
      Pattern propertyPattern = Pattern.compile(";?([^=]*)=([^;]*);?");
      Matcher propertyMatcher = propertyPattern.matcher(tail);
      while(propertyMatcher.find()) {
        extraProperties.put(propertyMatcher.group(1), propertyMatcher.group(2));
      }
    }
  }

  public String getKey() {
    return key;
  }

  public String getUrl() {
    return url;
  }

  public String get(String key) {
    return extraProperties.get(key);
  }
}
