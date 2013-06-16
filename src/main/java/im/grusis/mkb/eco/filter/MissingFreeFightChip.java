package im.grusis.mkb.eco.filter;

import im.grusis.mkb.eco.filter.common.ChipFilter;
import im.grusis.mkb.eco.filter.common.ChipType;
import im.grusis.mkb.eco.filter.common.CompareOperator;

/**
 * User: Mothership
 * Date: 13-6-16
 * Time: 上午2:21
 */
public class MissingFreeFightChip extends ChipFilter{

  public static int ChipMax = 3;

  public MissingFreeFightChip() {
    super(ChipType.FreeFight, CompareOperator.NotEqualTo, ChipMax);
  }
}
