package im.grusis.mkb.connection.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import im.grusis.mkb.XXTEA;

/**
 * User: Mothership
 * Date: 13-5-19
 * Time: 上午12:08
 */
public class ServerInformationResponse {

  public static final String ResultPattern = "muhe_result=\"([^\"]*)\";";
  public static final String ErrnoPattern = "muhe_errno=\"([^\"]*)\";";
  public static final String ErrstrPattern = "muhe_errstr=\"([^\"]*)\";";
  public static final String OutputPattern = "muhe_output=\"([^\"]*)\";";

  private String result;
  private String errno;
  private String errstr;
  private String output;

  public ServerInformationResponse(String responseString, String key) {
    Matcher resultMatcher = Pattern.compile(ResultPattern).matcher(responseString);
    if(resultMatcher.find()) result = resultMatcher.group(1);
    Matcher errnoMatcher = Pattern.compile(ErrnoPattern).matcher(responseString);
    if(errnoMatcher.find()) errno = errnoMatcher.group(1);
    Matcher errstrMatcher = Pattern.compile(ErrstrPattern).matcher(responseString);
    if(errstrMatcher.find()) errstr = errstrMatcher.group(1);
    Matcher outputMatcher = Pattern.compile(OutputPattern).matcher(responseString);
    if(outputMatcher.find()) output = outputMatcher.group(1);

    try {
      result = new String(XXTEA.decrypt(result, key).getBytes("UTF-8"), "UTF-16");
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public String getResult() {
    return result;
  }

  public String getErrno() {
    return errno;
  }

  public String getErrstr() {
    return errstr;
  }

  public String getOutput() {
    return output;
  }
}
