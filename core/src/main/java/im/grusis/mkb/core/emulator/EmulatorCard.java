package im.grusis.mkb.core.emulator;

import java.util.*;

import im.grusis.mkb.core.emulator.game.model.basic.*;
import im.grusis.mkb.core.emulator.game.model.response.*;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import im.grusis.mkb.core.service.AssetsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Mothership
 * Date: 13-6-28
 * Time: 下午11:28
 */
@Component
public class EmulatorCard {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorCard.class);

  @Autowired AssetsService assetsService;
  @Autowired AccountService accountService;
  @Autowired EmulatorCore core;

  public Map<Long, UserCard> getUserCards(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    Map<Long, UserCard> cards;
    if(refresh || (cards = account.getUserCards()) == null) {
      CardGetUserCardsResponse response = core.gameDoAction(username, "card.php", "GetUserCards", null, CardGetUserCardsResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      List<UserCard> cardList = response.getData().getCards();
      cards = new LinkedHashMap<Long, UserCard>();
      for(UserCard card : cardList) {
        cards.put(card.getUserCardId(), card);
      }
      account.setUserCards(cards);
      accountService.saveAccount(account);
    }
    return cards;
  }

  public Map<Long, Group> getCardGroup(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    Map<Long, Group> cardGroup;
    if(refresh || (cardGroup = account.getCardGroup()) == null) {
      CardGetCardGroupResponse response = core.gameDoAction(username, "card.php", "GetCardGroup", null, CardGetCardGroupResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      CardGroup cardGroups = response.getData();
      cardGroup = new TreeMap<Long, Group>();
      for(Group g : cardGroups.getGroups()) {
        cardGroup.put(g.getGroupId(), g);
      }
      account.setCardGroup(cardGroup);
      accountService.saveAccount(account);
    }
    return cardGroup;
  }


  public long setCardGroup(String username, Group group) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("Cards", group.getUserCardIds());
    params.put("Runes", group.getUserRuneIds());
    params.put("GroupId", Long.toString(group.getGroupId()));
    CardSetCardGroupResponse response = core.gameDoAction(username, "card.php", "SetCardGroup", params, CardSetCardGroupResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData().getGroupId();
  }

  public long setCardGroup(String username, List<Long> userCardIds, List<Long> userRuneIds, long groupId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("Cards", StringUtils.join(userCardIds, "_"));
    params.put("Runes", StringUtils.join(userRuneIds, "_"));
    params.put("GroupId", Long.toString(groupId));
    CardSetCardGroupResponse response = core.gameDoAction(username, "card.php", "SetCardGroup", params, CardSetCardGroupResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData().getGroupId();
  }

  public long getDefaultCardGroup(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    return -1;
  }

  public long setDefaultCardGroup(String username, long cardGroupId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    return -1;
  }

  public Map<Integer, SkillDef> getAllSkill(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<Integer, SkillDef> skills;
    if(refresh || (skills = assetsService.getSkillLookup()).isEmpty()) {
      CardGetAllSkillResponse response = core.gameDoAction(username, "card.php", "GetAllSkill", null, CardGetAllSkillResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      skills = assetsService.saveAssets(response.getData());
    }
    return skills;
  }

  public SkillDef getSkill(String username, int skillId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    SkillDef skill = assetsService.findSkill(skillId);
    if(skill == null) {
      skill = getAllSkill(username, true).get(skillId);
      if(skill == null) {
        throw new UnknownErrorException();
      }
    }
    return skill;
  }

  public Map<Integer, CardDef> getAllCard(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<Integer, CardDef> cards;
    if(refresh || (cards = assetsService.getCardLookup()).isEmpty()) {
      CardGetAllCardResponse response = core.gameDoAction(username, "card.php", "GetAllCard", null, CardGetAllCardResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      cards = assetsService.saveAssets(response.getData());
    }
    return cards;
  }

  public CardDef getCard(String username, int cardId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    if(cardId <= 0) {
      LOG.error("{} is an invalid card id. Card id must be positive integer", cardId);
    }
    CardDef card = assetsService.findCard(cardId);
    if(card == null) {
      getAllCard(username, true);
      card = assetsService.findCard(cardId);
      if(card == null) {
        throw new UnknownErrorException();
      }
    }
    return card;
  }

}
