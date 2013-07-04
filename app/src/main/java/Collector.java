import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * User: Mothership
 * Date: 13-7-3
 * Time: 下午10:20
 */
public class Collector {

  public static void main(String args[]) throws Exception {

    String ip = "http://tieba.baidu.com/p/2240653401";

    DefaultHttpClient dhc = new DefaultHttpClient();
    HttpGet get = new HttpGet(ip);

    HttpResponse response = dhc.execute(get);
    HttpEntity entity = response.getEntity();
    String content = StringEscapeUtils.unescapeJava(EntityUtils.toString(entity));

    System.out.println(content);
  }
}
