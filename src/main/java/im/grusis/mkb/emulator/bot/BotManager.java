package im.grusis.mkb.emulator.bot;

import im.grusis.mkb.emulator.dictionary.DictionaryManager;
import im.grusis.mkb.emulator.emulator.MkbEmulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 上午12:30
 */
@Component
public class BotManager {

  @Autowired DictionaryManager dictionaryManager;
  @Autowired MkbEmulator emulator;

  public void angelBot(String ud, String nd, int sex, long serverId, String inviteCode) {
    AngelBot ab = new AngelBot(dictionaryManager.getUsernameDict(ud), dictionaryManager.getNicknameDict(nd), sex, serverId, inviteCode, emulator);
    new Thread(ab).start();
  }

  //      public AngelBot(MkbDictionary usernameDict, MkbDictionary nicknameDict, int sex, long serverId, String defaultInviteCode, MkbEmulator emulator) {

}
