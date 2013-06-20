package im.grusis.mkb.eco.util.filter;

import im.grusis.mkb.eco.util.filter.operators.AndFilter;

/**
 * User: Mothership
 * Date: 13-6-19
 * Time: 下午11:31
 */
public class ShallDoFreeFight extends AndFilter {
  public ShallDoFreeFight() {
    super(new ReadyForFreeFight(), new MissingFreeFightChip());
  }
}
