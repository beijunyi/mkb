package im.grusis.mkb.web.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import im.grusis.mkb.core.emulator.AutomatedServiceEngine;
import im.grusis.mkb.core.emulator.MkbEmulator;
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
  @Autowired private AutomatedServiceEngine autoService;

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
  @Path("/refresh")
  public Response refreshUserInfo(@QueryParam("username") String username, @QueryParam("remote") boolean remote) throws MkbException {
    return Response.ok(emulator.gameGetUserInfo(username, remote)).build();
  }

  @GET
  @Path("/friends")
  public Response getFriends(@QueryParam("username") String username, @QueryParam("refresh") boolean refresh) throws MkbException {
    return Response.ok(emulator.gameGetFriends(username, refresh)).build();
  }

  @GET
  @Path("/cards")
  public Response getCards(@QueryParam("username") String username, @QueryParam("refresh") boolean refresh) throws MkbException {
    return Response.ok(emulator.gameGetUserCards(username, refresh)).build();
  }

  @GET
  @Path("/mazestatus")
  public Response getMazeStatus(@QueryParam("username") String username) throws MkbException {
    return Response.ok(autoService.getMazeStatus(username)).build();
  }

  @GET
  @Path("/resetmaze")
  public Response resetMaze(@QueryParam("username") String username, @QueryParam("id") int id) throws MkbException {
    return Response.ok(emulator.gameResetMaze(username, id)).build();
  }

  @GET
  @Path("/clearmaze")
  public Response clearMaze(@QueryParam("username") String username, @QueryParam("id") int id, @QueryParam("max") int max) throws MkbException {
    autoService.clearMaze(username, id, max, false, 0);
    return Response.ok(emulator.gameGetMazeStatus(username, id, false)).build();
  }

  @GET
  @Path("/counter")
  public Response getCounterAttacks(@QueryParam("username") String username) throws MkbException {
    return Response.ok(autoService.getCounterAttacks(username)).build();
  }

  @GET
  @Path("/attack")
  public Response attackStage(@QueryParam("username") String username, @QueryParam("id") int stageId) throws MkbException {
    emulator.gameMapBattleAuto(username, stageId);
    return Response.ok(emulator.gameGetMapStageDef(username, stageId)).build();
  }
}

