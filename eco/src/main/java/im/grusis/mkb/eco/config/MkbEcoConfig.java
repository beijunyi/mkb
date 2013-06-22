package im.grusis.mkb.eco.config;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.PostConstruct;

import im.grusis.mkb.core.config.MkbCoreConfig;
import im.grusis.mkb.eco.MkbEcoMarker;
import im.grusis.mkb.eco.heuristics.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * User: Junyi BEI
 * Date: 18/01/2013
 * Time: 22:58
 */
@Configuration
//@EnableScheduling
@PropertySource("classpath:/eco.properties")
@ComponentScan(basePackageClasses = MkbEcoMarker.class)
@Import({MkbCoreConfig.class, EcoUtilConfig.class, EcoFilterConfig.class, EcoProfileConfig.class})
public class MkbEcoConfig implements SchedulingConfigurer {

  private static final Logger Log = LoggerFactory.getLogger(MkbEcoConfig.class);

  @Autowired private Environment env;

  @Value("${system.timezone}") private String timezone;

  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone(timezone));
    Log.info("Timezone is set to {}, the current time is {}", timezone, new Date().toString());
  }


  @Bean(destroyMethod="shutdown")
  public ScheduledExecutorService taskScheduler() {
    return Executors.newScheduledThreadPool(42);
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.setScheduler(taskScheduler());
  }


  @Bean
  public KeepConfig getKeepConfig() {
    int min = env.getProperty(KeepConfig.KeepConfigPrefix + KeepConfig.KeepMin, Integer.class, KeepConfig.KeepMinDefault);
    boolean upgrade = env.getProperty(KeepConfig.KeepConfigPrefix + KeepConfig.KeepUpgrade, Boolean.class, KeepConfig.KeepUpgradeDefault);
    return new KeepConfig(min, upgrade);
  }

  @Bean
  public BeginnerConfigList getBeginnerConfigList() {
    BeginnerConfigList starterConfigMap = new BeginnerConfigList();
    int i = 1;
    String key;
    while(env.containsProperty((key = BeginnerConfig.Beginner + i))) {
      String cards = env.getProperty(key);
      String[] cardArray = cards.split(",");
      List<Integer> cardIdList = new ArrayList<Integer>();
      for(String card : cardArray) {
        cardIdList.add(Integer.valueOf(card));
      }
      starterConfigMap.add(new BeginnerConfig(cardIdList));
      i++;
    }
    return starterConfigMap;
  }

  @Bean
  public CardConfigMap getCardConfigMap() {
    CardConfigMap cardConfigMap = new CardConfigMap();
    int i = 1;
    String key;
    while(env.containsProperty((key = CardConfig.CardConfigPrefix + i))) {
      int id = env.getProperty(key, Integer.class);
      int upgrade = env.getProperty(key + CardConfig.UpgradeMaxNumberSuffix, Integer.class, Integer.MAX_VALUE);
      int keep = env.getProperty(key + CardConfig.KeepMaxNumberSuffix, Integer.class, Integer.MAX_VALUE);
      cardConfigMap.put(id, new CardConfig(id, upgrade, keep));
      i++;
    }
    return cardConfigMap;
  }

  @Bean
  public SystemConfig getSystemConfig() {
    return new SystemConfig(env.getProperty(SystemConfig.SystemConfigPrefix + SystemConfig.Size, Integer.class));
  }

  @Bean
  public ScoreAffectTypeConfigMap getSkillAffectTypeConfigMap() {
    ScoreAffectTypeConfigMap scoreAffectTypeConfigMap = new ScoreAffectTypeConfigMap();
    int i = 1;
    String key;
    int score, base;
    double discount;
    boolean inverse;
    String[] types;
    while(env.containsProperty((key = ScoreAffectTypeConfig.SkillAffectTypeConfigPrefix + i))) {
      score = env.getProperty(key, Integer.class);
      if(score == -1) {
        types = env.getProperty(key + ScoreAffectTypeConfig.SubTypes).split(",");
        key += ScoreAffectTypeConfig.SubType;
        Map<Integer, SubType> subTypes = new LinkedHashMap<Integer, SubType>();
        String subKey;
        for(String type : types) {
          subKey = key + type;
          score = env.getProperty(subKey, Integer.class);
          int j = 1;
          List<ScoreBase> scoreBases = new ArrayList<ScoreBase>();
          String scoreBaseKey = subKey + ScoreAffectTypeConfig.ScoreBase;
          String subScoreBaseKey;
          while(env.containsProperty((subScoreBaseKey = scoreBaseKey + j))) {
            base = env.getProperty(subScoreBaseKey, Integer.class);
            discount = env.getProperty(subScoreBaseKey + ScoreAffectTypeConfig.ScoreDiscount, Double.class, 1d);
            inverse = env.getProperty(subScoreBaseKey + ScoreAffectTypeConfig.ScoreInverse, Boolean.class, false);
            scoreBases.add(new ScoreBase(base, discount, inverse));
            j++;
          }
          subTypes.put(Integer.valueOf(type), new SubType(j, score, scoreBases));
        }
        scoreAffectTypeConfigMap.put(i, new ScoreAffectTypeConfig(i, subTypes));
        i++;
        continue;
      }
      int j = 1;
      List<ScoreBase> scoreBases = new ArrayList<ScoreBase>();
      String scoreBaseKey = key + ScoreAffectTypeConfig.ScoreBase;
      String subScoreBaseKey;
      while(env.containsProperty((subScoreBaseKey = scoreBaseKey + j))) {
        base = env.getProperty(subScoreBaseKey, Integer.class);
        discount = env.getProperty(subScoreBaseKey + ScoreAffectTypeConfig.ScoreDiscount, Double.class, 1d);
        inverse = env.getProperty(subScoreBaseKey + ScoreAffectTypeConfig.ScoreInverse, Boolean.class, false);
        scoreBases.add(new ScoreBase(base, discount, inverse));
        j++;
      }
      scoreAffectTypeConfigMap.put(i, new ScoreAffectTypeConfig(i, score, scoreBases));
      i++;
    }
    return scoreAffectTypeConfigMap;
  }

  @Bean
  public ScoreCardAttributeConfig getScoreCardAttributeConfig() {
    return new ScoreCardAttributeConfig(env.getProperty(ScoreCardAttributeConfig.CardAttributeConfigPrefix + ScoreCardAttributeConfig.AttackDiscount, Double.class),
                                         env.getProperty(ScoreCardAttributeConfig.CardAttributeConfigPrefix + ScoreCardAttributeConfig.HPDiscount, Double.class),
                                         env.getProperty(ScoreCardAttributeConfig.CardAttributeConfigPrefix + ScoreCardAttributeConfig.HPPenalty, Integer.class),
                                         env.getProperty(ScoreCardAttributeConfig.CardAttributeConfigPrefix + ScoreCardAttributeConfig.HPPenaltyBase, Integer.class),
                                         env.getProperty(ScoreCardAttributeConfig.CardAttributeConfigPrefix + ScoreCardAttributeConfig.CDBase, Integer.class),
                                         env.getProperty(ScoreCardAttributeConfig.CardAttributeConfigPrefix + ScoreCardAttributeConfig.CDDiscount, Double.class),
                                         env.getProperty(ScoreCardAttributeConfig.CardAttributeConfigPrefix + ScoreCardAttributeConfig.CDPenalty, Integer.class),
                                         env.getProperty(ScoreCardAttributeConfig.CardAttributeConfigPrefix + ScoreCardAttributeConfig.CDPenaltyException));
  }

}
