package im.grusis.mkb.eco;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import im.grusis.mkb.core.emulator.game.model.basic.CardDef;
import im.grusis.mkb.core.emulator.game.model.basic.SkillDef;
import im.grusis.mkb.eco.heuristics.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.log4testng.Logger;

/**
 * User: beij
 * Date: 17/06/13
 * Time: 13:36
 */
@Component
public class EcoMind {

  public static final Logger Log = Logger.getLogger(EcoMind.class);

  @Autowired ScoreAffectTypeConfigMap affectTypes;
  @Autowired ScoreCardAttributeConfig cardAttributeConfig;

  public double getSkillScore(SkillDef skill, Map<Integer, SkillDef> skillMap) {
    int skillType = skill.getAffectType();
    ScoreAffectTypeConfig config = affectTypes.get(skillType);
    List<ScoreBase> scoreBases = config.getScoreBases();
    double score;
    if(scoreBases == null) {
      SubType subType = config.getSubTypes().get(skill.getType());
      scoreBases = subType.getScoreBases();
      score = subType.getScore();
    } else {
      score = config.getScore();
    }
    int size = scoreBases.size();
    ScoreBase scoreBase;
    if(size > 0) {
      int current = skill.getAffectValue();
      if(skillType == 53 || skillType == 59) {
        current = skillMap.get(current).getAffectValue();
      }
      scoreBase = scoreBases.get(0);
      int base = scoreBase.getBase();
      if(scoreBase.isInverse()) {
        score *= 1d - (scoreBase.getDiscount() * (current - base) / base);
      } else {
        score *= 1d - (scoreBase.getDiscount() * (base - current) / base);
      }
      if(size > 1) {
        current = skill.getAffectValue2();
        if(skillType == 53 || skillType == 59) {
          current = skillMap.get(current).getAffectValue2();
        }
        scoreBase = scoreBases.get(1);
        base = scoreBase.getBase();
        if(scoreBase.isInverse()) {
          score *= 1d - (scoreBase.getDiscount() * (current - base) / base);
        } else {
          score *= 1d - (scoreBase.getDiscount() * (base - current) / base);
        }
      }
    }
    return score;
  }

  public double getSkillScore(String skillId, Map<Integer, SkillDef> skillMap) {
    double score = 0;
    String[] skills = skillId.split("_");
    for(String skill : skills) {
      if(skill.isEmpty()) break;
      score += getSkillScore(skillMap.get(Integer.valueOf(skill)), skillMap);
    }
    return score;
  }

  public String getDetailSkillScore(String skillId, Map<Integer, SkillDef> skillMap) {
    String desc = "";
    String[] skills = skillId.split("_");
    for(String id : skills) {
      if(id.isEmpty()) break;
      SkillDef skill = skillMap.get(Integer.valueOf(id));
      int score = (int)getSkillScore(skill, skillMap);
      desc += score + " scores from " + skill.getName() + ", ";
    }
    return desc;
  }

  public List<Integer> parseSkillId(String skillIds) {
    List<Integer> ret = new ArrayList<Integer>();
    String[] skills = skillIds.split("_");
    for(String id : skills) {
      if(id.isEmpty()) break;
      ret.add(Integer.valueOf(id));
    }
    return ret;
  }

  public double getCardScore(CardDef card, int level, Map<Integer, SkillDef> skillMap) {
    double score = 0;
    int atk = card.getAttackArray()[level];
    int hp = card.getHpArray()[level];
    score += cardAttributeConfig.getAtkDiscount() * atk;
    score += cardAttributeConfig.getHpDiscount() * hp;
    score += getSkillScore(card.getSkill(), skillMap);
    if(level >= 5) {
      score += getSkillScore(card.getLockSkill1(), skillMap);
      if(level >= 10) {
        score += getSkillScore(card.getLockSkill2(), skillMap);
      }
    }
    int cd = card.getWait();
    int cdBase = cardAttributeConfig.getCdBase();
    double cdDiscount = cardAttributeConfig.getCdDiscount();
    score += cdDiscount * (cdBase - cd);
    if(hp < cardAttributeConfig.getHpPenaltyBase()) {
      score -= cardAttributeConfig.getHpPenalty();
    }
    if(cd == cdBase) {
      List<Integer> skillIds = new ArrayList<Integer>();
      skillIds.addAll(parseSkillId(card.getSkill()));
      skillIds.addAll(parseSkillId(card.getLockSkill1()));
      skillIds.addAll(parseSkillId(card.getLockSkill2()));
      for(int e : cardAttributeConfig.getCdPenaltyExceptions()) {
        if(skillIds.contains(e)) {
          return score;
        }
      }
      score -= cardAttributeConfig.getCdPenalty();
    }
    return score;
  }

  public String getDetailCardScore(CardDef card, int level, Map<Integer, SkillDef> skillMap) {
    String desc = "";
    int atk = card.getAttackArray()[level];
    int hp = card.getHpArray()[level];
    desc += (int)(cardAttributeConfig.getAtkDiscount() * atk) + " scores from atk " + atk + ", ";
    desc += (int)(cardAttributeConfig.getHpDiscount() * hp) + " scores from hp "  + hp + ", ";
    desc += (getDetailSkillScore(card.getSkill(), skillMap));
    if(level >= 5) {
      desc += (getDetailSkillScore(card.getLockSkill1(), skillMap));
      if(level >= 10) {
        desc += (getDetailSkillScore(card.getLockSkill2(), skillMap));
      }
    }
    int cd = card.getWait();
    int cdBase = cardAttributeConfig.getCdBase();
    double cdDiscount = cardAttributeConfig.getCdDiscount();
    desc += (int)(cdDiscount * (cdBase - cd)) + " scores from cd " + cd;
    return desc;
  }

}
