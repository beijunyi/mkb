package im.grusis.mkb.web.controller;

import java.util.Collection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import im.grusis.mkb.core.emulator.MkbEmulator;
import im.grusis.mkb.core.emulator.game.model.basic.Friend;
import im.grusis.mkb.core.emulator.game.model.basic.UserCard;
import im.grusis.mkb.core.exception.MkbException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * User: Mothership
 * Date: 13-5-26
 * Time: 下午8:15
 */
@Controller
@Path("api/account")
public class AccountController {
  private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(AccountController.class);

  @Autowired private AccountService accountService;
  @Autowired private MkbEmulator emulator;

  @GET
  @Path("/login")
  public Response login(@QueryParam("username") String username, @QueryParam("password") String password, @QueryParam("refresh") boolean refresh) throws MkbException {
    MkbAccount account = accountService.findAccountByUsername(username);
    if(account == null || (!account.getPassword().equals(password) && !password.isEmpty())) {
      emulator.webLogin(username, password);
      emulator.gamePassportLogin(username);
    }
    return Response.ok(emulator.gameGetUserInfo(username, false)).build();
  }

  @GET
  @Path("/friends")
  public Response getFriends(@QueryParam("username") String username, @QueryParam("refresh") boolean refresh) throws MkbException {
    Collection<Friend> friends = emulator.gameGetFriends(username, refresh).values();
    return Response.ok(friends).build();
  }

  @GET
  @Path("/cards")
  public Response getCards(@QueryParam("username") String username, @QueryParam("refresh") boolean refresh) throws MkbException {
    Collection<UserCard> cards = emulator.gameGetUserCards(username, refresh).values();
    return Response.ok(cards).build();
  }

}
