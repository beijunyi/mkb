package im.grusis.mkb.core.repository.model;

import im.grusis.mkb.core.emulator.game.model.basic.AllSkill;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午8:07
 */
public class SkillAssets extends MkbAssets<AllSkill> {

  public static final String AssetName = "skills";

  public SkillAssets() {
    super(AssetName);
  }
}
