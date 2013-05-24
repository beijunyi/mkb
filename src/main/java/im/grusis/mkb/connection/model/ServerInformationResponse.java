package im.grusis.mkb.connection.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import im.grusis.mkb.StringHelper;
import im.grusis.mkb.Unserializer;
import im.grusis.mkb.XXTEA;
import im.grusis.mkb.connection.model.response.ServerInformation;
import org.apache.commons.lang3.StringEscapeUtils;

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

  public ServerInformationResponse() {
  }

  public ServerInformationResponse(String responseString, String key) {
    Matcher resultMatcher = Pattern.compile(ResultPattern).matcher(responseString);
    if(resultMatcher.find()) result = resultMatcher.group(1);
    Matcher errnoMatcher = Pattern.compile(ErrnoPattern).matcher(responseString);
    if(errnoMatcher.find()) errno = errnoMatcher.group(1);
    Matcher errstrMatcher = Pattern.compile(ErrstrPattern).matcher(responseString);
    if(errstrMatcher.find()) errstr = errstrMatcher.group(1);
    Matcher outputMatcher = Pattern.compile(OutputPattern).matcher(responseString);
    if(outputMatcher.find()) output = outputMatcher.group(1);

    result = StringEscapeUtils.unescapeJava(result.replace("\\x", "\\u00"));
    String decryptedResult = XXTEA.decrypt(result, key);
    result = Unserializer.UnserializeString(StringHelper.toUTF16(decryptedResult));
  }

  public void setResult(String result) {
    this.result = result;
  }

  public void setErrno(String errno) {
    this.errno = errno;
  }

  public void setErrstr(String errstr) {
    this.errstr = errstr;
  }

  public void setOutput(String output) {
    this.output = output;
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

  public ServerInformation getModel() {
    String fixed = StringEscapeUtils.unescapeJava(result.replace("\"[", "[").replace("]\"", "]"));
    return new Gson().fromJson(fixed, ServerInformation.class);
  }
}
