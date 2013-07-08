package im.grusis.mkb.eco.model;

public class FriendsSettings {
  private int deleteInactivate;
  private int minFriends;

  public FriendsSettings() {
    deleteInactivate = 5;
    minFriends = 20;
  }

  public int getDeleteInactivate() {
    return deleteInactivate;
  }

  public void setDeleteInactivate(int deleteInactivate) {
    this.deleteInactivate = deleteInactivate;
  }

  public int getMinFriends() {
    return minFriends;
  }

  public void setMinFriends(int minFriends) {
    this.minFriends = minFriends;
  }
}
