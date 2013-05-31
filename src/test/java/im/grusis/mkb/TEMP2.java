package im.grusis.mkb;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 下午8:22
 */
public class TEMP2 {

//  @Test
  public void t() {
    final ClientConnectionManager ccm = new PoolingClientConnectionManager();
    for(int i = 0; i < 100; i++) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            DefaultHttpClient hc = new DefaultHttpClient(ccm);
            HttpGet get = new HttpGet("http://www.google.com");
            HttpResponse response = hc.execute(get);
            BasicResponseHandler handler = new BasicResponseHandler();
            String responseString = handler.handleResponse(response);
            System.out.println(responseString);
          } catch(Exception e) {
            e.printStackTrace();
          }
        }
      }).start();

    }

    System.currentTimeMillis();
  }

  public static void main(String args[]) {
    new TEMP2().t();
  }
}
