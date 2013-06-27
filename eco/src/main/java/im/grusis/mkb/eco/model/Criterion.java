package im.grusis.mkb.eco.model;

import java.util.*;

/**
 * User: Mothership
 * Date: 13-6-27
 * Time: 上午12:44
 */
public abstract class Criterion<T extends Criteria> {

  protected String name;
  protected Map<Integer, T> criterion;
  protected int nextId;

  protected Criterion(String name) {
    this.name = name;
    nextId = 0;
  }

  public String getName() {
    return name;
  }

  public Collection<T> getCriterion() {
    return criterion.values();
  }

  public void addCriteria(T criteria) {
    int id = nextId++;
    criteria.setId(id);
    if(criterion == null) {
      criterion = new TreeMap<Integer, T>();
    }
    criterion.put(id, criteria);
  }

  public void removeCriteria(int id) {
    if(criterion != null) {
      criterion.remove(id);
    }
  }

  public T getCriteria(int id) {
    if(criterion != null) {
      return criterion.get(id);
    }
    return null;
  }
}
