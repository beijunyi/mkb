package im.grusis.mkb.core.filter;

import im.grusis.mkb.core.filter.common.ChipFilter;
import im.grusis.mkb.core.filter.common.ChipType;
import im.grusis.mkb.core.filter.common.CompareOperator;

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
