import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * User: Mothership
 * Date: 13-7-3
 * Time: 下午9:42
 */
public class Voter {


  public static void main(String args[]) throws Exception {

    String ip = "http://www.cwan.com/zt/mkhx/moka_info.php";

    DefaultHttpClient dhc = new DefaultHttpClient();
    HttpPost post = new HttpPost(ip);
    post.setHeader("X-Forwarded-For", "212.13.207.171");
    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    nvps.add(new BasicNameValuePair("type", "2"));
    nvps.add(new BasicNameValuePair("id", "564239"));
    nvps.add(new BasicNameValuePair("genre", "2"));
    nvps.add(new BasicNameValuePair("area", "降临天使"));
    nvps.add(new BasicNameValuePair("role", "伯特恩"));
    nvps.add(new BasicNameValuePair("mail", "fashionshow@sina.com"));
    nvps.add(new BasicNameValuePair("level", "3"));
    nvps.add(new BasicNameValuePair("typeGd", ""));
    post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

    HttpResponse response = dhc.execute(post);
    HttpEntity entity = response.getEntity();
    String content = StringEscapeUtils.unescapeJava(EntityUtils.toString(entity));

    System.out.println(content);
  }
}
