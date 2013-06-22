package im.grusis.mkb.web.model;

import im.grusis.mkb.core.emulator.game.model.basic.UserCard;

/**
 * User: Mothership
 * Date: 13-6-22
 * Time: 上午2:04
 */
public class WebCard extends UserCard {
  private String name;

  public WebCard(UserCard userCard, String name) {
    Uid = userCard.getUid();
    CardId = userCard.getCardId();
    Level = userCard.getLevel();
    Exp = userCard.getLevel();
    UserCardId = userCard.getUserCardId();
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
