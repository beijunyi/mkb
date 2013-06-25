package im.grusis.mkb.web.model;

import java.util.List;
import java.util.Map;

/**
 * User: Mothership
 * Date: 13-6-25
 * Time: 下午9:18
 */
public class FindAccountRequest {
  private List<Map<String, String>> filters;

  public List<Map<String, String>> getFilters() {
    return filters;
  }

  public void setFilters(List<Map<String, String>> filters) {
    this.filters = filters;
  }
}
