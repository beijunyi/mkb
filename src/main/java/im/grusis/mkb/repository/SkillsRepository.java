package im.grusis.mkb.repository;

import im.grusis.mkb.emulator.emulator.core.model.basic.AllSkill;
import im.grusis.mkb.emulator.emulator.core.model.basic.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * User: Mothership
 * Date: 13-5-27
 * Time: 下午10:03
 */
@Repository
public class SkillsRepository extends MkbRepository<Skill> {

  private static final Logger Log = LoggerFactory.getLogger(SkillsRepository.class);

  public SkillsRepository() {
    super("skills", Skill.class);
  }

  public Skill getSkill(int skillId) {
    return read(Integer.toString(skillId));
  }

  public void createOrUpdateSkill(Skill skill) {
    String index = Integer.toString(skill.getSkillId());
    if(index == null || index.isEmpty()) {
      Log.error("Cannot create or update skill with no skill id.");
      return;
    }
    write(index, skill, true);
  }

  public void saveSkills(AllSkill allSkill) {
    for(Skill skill : allSkill.getSkills()) {
      createOrUpdateSkill(skill);
    }
  }

}
