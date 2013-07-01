package im.grusis.mkb.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import im.grusis.mkb.core.config.MkbCoreConfig;
import im.grusis.mkb.core.emulator.MkbEmulator;
import im.grusis.mkb.core.emulator.game.model.basic.Group;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * User: Mothership
 * Date: 13-6-30
 * Time: 下午10:43
 */
public class SetCardGroup {

  public static void main(String args[]) throws Exception {
    String username = "tug100";
    long cardGroupId = 72780;

    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MkbCoreConfig.class);
    ctx.start();
    List<Long> userCardIds = new ArrayList<Long>();
    MkbEmulator emulator = ctx.getBean(MkbEmulator.class);

    Map<Long, Group> cardGroups = emulator.card().getCardGroup(username, true);

    Group group = cardGroups.get(cardGroupId);

    group.setUserCardIds("1090473_2579394_4754839_10718347_10719299_4827673_4830730_4839368_10718615_10421947");
    emulator.card().setCardGroup(username, group);
    System.out.println(System.currentTimeMillis());

  }
}
