package im.grusis.mkb.connection.core.model.basic;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-5-29
 * Time: 下午11:15
 */
public class CardGroup {
  private List<Group> Groups;

  public List<Group> getGroups() {
    return Groups;
  }

  public void setGroups(List<Group> groups) {
    Groups = groups;
  }
}
