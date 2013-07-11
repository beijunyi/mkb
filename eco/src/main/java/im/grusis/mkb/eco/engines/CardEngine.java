package im.grusis.mkb.eco.engines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import im.grusis.mkb.core.emulator.EmulatorCard;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardEngine {
  private static final Logger LOG = LoggerFactory.getLogger(MapEngine.class);

  @Autowired EmulatorCard card;

  public void switchCardGroup(String username, int cardGroupIndex) throws ServerNotAvailableException, WrongCredentialException, UnknownErrorException {
    List<Long> cardGroups = new ArrayList<Long>(card.getCardGroup(username, false).keySet());
    Collections.sort(cardGroups);
    if(cardGroups.size() < cardGroupIndex) {
      LOG.warn("{} does not have card group {}", username, cardGroupIndex);
    } else {
      long current = card.getDefaultCardGroup(username, false);
      long desired = cardGroups.get(cardGroupIndex - 1);
      if(current != desired) {
        card.setDefaultCardGroup(username, desired);
      }
    }
  }

}
