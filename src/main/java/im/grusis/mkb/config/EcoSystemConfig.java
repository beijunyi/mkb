package im.grusis.mkb.config;

import java.util.ArrayList;
import java.util.List;

import im.grusis.mkb.eco.EcoSystemMaster;
import im.grusis.mkb.eco.configs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

/**
 * User: Junyi BEI
 * Date: 18/01/2013
 * Time: 22:58
 */
@Configuration
@PropertySource("classpath:/eco.properties")
@ComponentScan(basePackageClasses = EcoSystemMaster.class)
public class EcoSystemConfig {


  @Autowired private Environment env;

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

}
