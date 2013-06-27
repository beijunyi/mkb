package im.grusis.mkb.eco.model;

/**
 * User: Mothership
 * Date: 13-6-27
 * Time: 上午12:45
 */
public abstract class Criteria {
  protected int id;
  protected String comment;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

}
