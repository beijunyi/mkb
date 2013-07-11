package im.grusis.mkb.eco.config;

import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import javax.annotation.PostConstruct;

import im.grusis.mkb.core.config.MkbCoreConfig;
import im.grusis.mkb.eco.MkbEcoMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
//@EnableScheduling
@PropertySource("classpath:/eco.properties")
@ComponentScan(basePackageClasses = MkbEcoMarker.class)
@Import({MkbCoreConfig.class, EcoUtilConfig.class, EcoFilterConfig.class, EcoProfileConfig.class})
public class MkbEcoConfig implements SchedulingConfigurer {

  private static final Logger Log = LoggerFactory.getLogger(MkbEcoConfig.class);

  @Autowired private ExecutorService executorService;

  @Value("${system.timezone}") private String timezone;

  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone(timezone));
    Log.info("Timezone is set to {}, the current time is {}", timezone, new Date().toString());
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.setScheduler(executorService);
  }

}
