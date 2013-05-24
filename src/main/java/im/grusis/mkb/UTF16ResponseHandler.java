package im.grusis.mkb;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.util.EntityUtils;

/**
 * User: Mothership
 * Date: 13-5-23
 * Time: 下午10:04
 */
public class UTF16ResponseHandler {

  public String handleResponse(final HttpResponse response)
    throws HttpResponseException, IOException {
    StatusLine statusLine = response.getStatusLine();
    HttpEntity entity = response.getEntity();
    if(statusLine.getStatusCode() >= 300) {
      EntityUtils.consume(entity);
      throw new HttpResponseException(statusLine.getStatusCode(),
                                       statusLine.getReasonPhrase());
    }
    return entity == null ? null : EntityUtils.toString(entity, "ISO-8859-1");
  }

}
