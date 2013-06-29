package im.grusis.mkb.core.emulator;

import java.util.LinkedHashMap;
import java.util.Map;

import im.grusis.mkb.core.emulator.game.model.basic.Friend;
import im.grusis.mkb.core.emulator.game.model.basic.FriendApplys;
import im.grusis.mkb.core.emulator.game.model.response.FriendDisposeFriendApplyResponse;
import im.grusis.mkb.core.emulator.game.model.response.FriendGetFriendApplysResponse;
import im.grusis.mkb.core.emulator.game.model.response.FriendGetFriendsResponse;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
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
public class EmulatorFriend {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorFriend.class);

  @Autowired AccountService accountService;
  @Autowired EmulatorCore core;
  @Autowired EmulatorUser user;

  public Map<Long, Friend> getFriends(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    MkbAccount account = accountService.findAccountByUsername(username);
    Map<Long, Friend> friends;
    if(refresh || (friends = account.getFriendMap()) == null) {
      FriendGetFriendsResponse response = core.gameDoAction(username, "friend.php", "GetFriends", null, FriendGetFriendsResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      friends = account.setFriends(response.getData());
      accountService.saveAccount(account);
    }
    return friends;
  }

  public Friend getFriend(String username, long fid) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    return getFriends(username, false).get(fid);
  }

  public FriendApplys getFriendApplys(String username) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    FriendGetFriendApplysResponse response = core.gameDoAction(username, "friend.php", "GetFriendApplys", null, FriendGetFriendApplysResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return response.getData();
  }

  public boolean disposeFriendApply(String username, long friendId, boolean accept) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Map<String, String> params = new LinkedHashMap<String, String>();
    String type;
    if(accept) {
      type = "agree";
    } else {
      type = "reject";
    }
    params.put("type", type);
    params.put("Fid", Long.toString(friendId));
    FriendDisposeFriendApplyResponse response = core.gameDoAction(username, "friend.php", "DisposeFriendApply", params, FriendDisposeFriendApplyResponse.class);
    if(response.badRequest()) {
      throw new UnknownErrorException();
    }
    return true;
  }
}
