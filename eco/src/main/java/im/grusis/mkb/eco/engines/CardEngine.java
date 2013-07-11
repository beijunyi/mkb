package im.grusis.mkb.eco.engines;

import java.util.*;

import im.grusis.mkb.core.emulator.EmulatorCard;
import im.grusis.mkb.core.emulator.game.model.basic.Group;
import im.grusis.mkb.core.emulator.game.model.basic.UserCard;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.eco.model.Rating;
import im.grusis.mkb.eco.model.RatingSettings;
import im.grusis.mkb.eco.service.EcoSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardEngine {
  private static final Logger LOG = LoggerFactory.getLogger(MapEngine.class);

  @Autowired EmulatorCard card;
  @Autowired EcoSettingsService ecoSettingsService;

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

  public void optimizeCardGroup(String username) throws ServerNotAvailableException, WrongCredentialException, UnknownErrorException {
    RatingSettings ratingSettings = ecoSettingsService.getCardRatingSettings();
    if(ratingSettings == null) return;
    Map<Integer, Rating> cardRatingMap = ratingSettings.getRatings();

    long cardGroupId = card.getDefaultCardGroup(username, false);
    Map<Long, Group> groups = card.getCardGroup(username, false);
    Group group = groups.get(cardGroupId);
    Collection<UserCard> userCards = card.getUserCards(username, false).values();
    List<UserCard> lv10 = new ArrayList<UserCard>();
    List<UserCard> lv5 = new ArrayList<UserCard>();
    List<UserCard> lv0 = new ArrayList<UserCard>();
    for(UserCard userCard : userCards) {
      int level = userCard.getLevel();
      if(level >= 10) {
        lv10.add(userCard);
      } else if(level >= 5) {
        lv5.add(userCard);
      } else {
        lv0.add(userCard);
      }
    }
    List<UserCard> optimized = new ArrayList<UserCard>();
    for(UserCard card : lv10) {

    }
  }

}
