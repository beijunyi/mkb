package im.grusis.mkb.core.bot;


import java.util.*;

import im.grusis.mkb.core.emulator.MkbEmulator;
import im.grusis.mkb.core.emulator.game.model.basic.Group;
import im.grusis.mkb.core.emulator.game.model.basic.UserCardInfo;
import im.grusis.mkb.core.exception.MkbException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: beij
 * Date: 21/06/13
 * Time: 17:21
 */
public class StrengCardBot extends MkbBot {

  private static final Logger LOG = LoggerFactory.getLogger(StrengCardBot.class);

  private String username;
  private long userCardId;
  private MkbEmulator emulator;

  public StrengCardBot(String username, long userCardId, MkbEmulator emulator) {
    this.username = username;
    this.userCardId = userCardId;
    this.emulator = emulator;
  }

  @Override
  public void run() {
    try {
      Map<Long, UserCardInfo> userCards = emulator.gameGetUserCards(username, true);
      Map<Long, Group> groups = emulator.gameGetCardGroup(username, true);
      Set<Long> inUse = new HashSet<Long>();
      for(Group group : groups.values()) {
        List<UserCardInfo> cards = group.getUserCardInfo();
        for(UserCardInfo card : cards) {
          inUse.add(card.getUserCardId());
        }
      }

    } catch(MkbException e) {
      LOG.error("*** UNKNOWN ERROR ***", e);
    }
  }
}
