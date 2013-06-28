package im.grusis.mkb.core.bot;

import java.util.*;
import java.util.concurrent.Executor;

import im.grusis.mkb.core.emulator.MkbEmulator;
import im.grusis.mkb.core.emulator.game.model.basic.Group;
import im.grusis.mkb.core.emulator.game.model.basic.UserCard;
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

  public StrengCardBot(String username, MkbEmulator emulator) {
    super(username, emulator);
  }

  @Override
  protected Object bot() throws MkbException {
    Map<Long, UserCard> userCards = emulator.gameGetUserCards(username, true);
    Map<Long, Group> groups = emulator.gameGetCardGroup(username, true);
    Set<Long> inUse = new HashSet<Long>();
    for(Group group : groups.values()) {
      List<UserCard> cards = group.getUserCardInfo();
      for(UserCard card : cards) {
        inUse.add(card.getUserCardId());
      }
    }
    return null;
  }

}
