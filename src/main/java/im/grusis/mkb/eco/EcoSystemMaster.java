package im.grusis.mkb.eco;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * User: Mothership
 * Date: 13-6-6
 * Time: 下午8:16
 */
@Component
public class EcoSystemMaster {

  private static final Logger Log = LoggerFactory.getLogger(EcoSystemMaster.class);

  @Scheduled(fixedDelay = 5 * 60 * 1000)
  public void clearLaggard() {
  }
}
