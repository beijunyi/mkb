package im.grusis.mkb.emulator.bot;

import im.grusis.mkb.emulator.bot.password.HashPasswordGenerator;
import im.grusis.mkb.emulator.dictionary.MkbDictionary;
import im.grusis.mkb.emulator.emulator.MkbEmulator;
import im.grusis.mkb.emulator.emulator.core.model.basic.Card;
import im.grusis.mkb.emulator.emulator.core.model.basic.UserInfo;
import im.grusis.mkb.util.MacAddressHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 上午12:28
 */
public class AngelBot extends Bot {

  private static final Logger Log = LoggerFactory.getLogger(AngelBot.class);

  protected MkbDictionary usernameDict;
  protected MkbDictionary nicknameDict;
  protected int sex;
  protected long serverId;
  protected String defaultInviteCode;
  protected MkbEmulator emulator;

  public AngelBot(MkbDictionary usernameDict, MkbDictionary nicknameDict, int sex, long serverId, String defaultInviteCode, MkbEmulator emulator) {
    this.usernameDict = usernameDict;
    this.nicknameDict = nicknameDict;
    this.sex = sex;
    this.serverId = serverId;
    this.defaultInviteCode = defaultInviteCode;
    this.emulator = emulator;
  }

  @Override
  public void run() {
    UserInfo previous = null;
    while(usernameDict.hasNext() && nicknameDict.hasNext()) {
      String username = usernameDict.next();
      String nickname = nicknameDict.next();
      String password = new HashPasswordGenerator().generate(username);
      String mac = MacAddressHelper.getMacAddress();
      String inviteCode;
      if(previous == null) {
        inviteCode = defaultInviteCode;
      } else {
        inviteCode = previous.getInviteCode();
      }

      try {
        if(!emulator.webReg(username, password, mac, serverId)) continue;
        emulator.webLogin(username);
        emulator.gamePassportLogin(username);
        while(!emulator.gameSetNickname(username, sex, inviteCode, nickname) && nicknameDict.hasNext()) {
          nickname = nicknameDict.next();
        }
        int cardId = emulator.gamePurchase(username, 3);
        Card card = emulator.gameGetCardDetail(username, cardId);
        Log.info("{} {} obtained {} stars card {} {}", username, nickname, card.getColor(), card.getCardId(), card.getCardName());
        emulator.gameSkipTutorial(username, "1_21");
        emulator.gameSkipTutorial(username, "2_10");
        emulator.gameSkipTutorial(username, "2_17");
        UserInfo userInfo = emulator.gameGetUserInfo(username);
        if(previous != null) {
          String prevUsername = previous.getUserName();
          emulator.gameAcceptRewards(prevUsername);
          cardId = emulator.gamePurchase(prevUsername, 3);
          card = emulator.gameGetCardDetail(prevUsername, cardId);
          Log.info("{} {} obtained {} stars card {} {}", prevUsername, previous.getNickName(), card.getColor(), card.getCardId(), card.getCardName());
        }
        previous = userInfo;

      } catch(Exception e) {
        Log.error("*** UNKNOWN ERROR ***", e);
      }

    }
  }
}
