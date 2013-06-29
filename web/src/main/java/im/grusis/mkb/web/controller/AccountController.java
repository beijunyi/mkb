package im.grusis.mkb.web.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import im.grusis.mkb.core.emulator.*;
import im.grusis.mkb.core.emulator.engines.MapEngine;
import im.grusis.mkb.core.exception.MkbException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import im.grusis.mkb.web.model.ClearCounterAttackRequest;
import im.grusis.mkb.web.model.ClearCounterAttackResponse;
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
  @Autowired private EmulatorWeb web;
  @Autowired private EmulatorLogin login;
  @Autowired private EmulatorUser user;
  @Autowired private EmulatorFriend friend;
  @Autowired private EmulatorCard card;
  @Autowired private EmulatorMaze maze;
  @Autowired private EmulatorMapStage mapStage;
  @Autowired private MapEngine mapEngine;

  @GET
  @Path("/login")
  public Response login(@QueryParam("username") String username, @QueryParam("password") String password, @QueryParam("refresh") boolean refresh) throws MkbException {
    MkbAccount account = accountService.findAccountByUsername(username);
    if(account == null || (!account.getPassword().equals(password) && !password.isEmpty())) {
      web.login(username, password);
      login.passportLogin(username);
    }
    return Response.ok(user.getUserInfo(username, false)).build();
  }

  @GET
  @Path("/refresh")
  public Response refreshUserInfo(@QueryParam("username") String username, @QueryParam("remote") boolean remote) throws MkbException {
    return Response.ok(user.getUserInfo(username, remote)).build();
  }

  @GET
  @Path("/friends")
  public Response getFriends(@QueryParam("username") String username, @QueryParam("refresh") boolean refresh) throws MkbException {
    return Response.ok(friend.getFriends(username, refresh)).build();
  }

  @GET
  @Path("/cards")
  public Response getCards(@QueryParam("username") String username, @QueryParam("refresh") boolean refresh) throws MkbException {
    return Response.ok(card.getUserCards(username, refresh)).build();
  }

  @GET
  @Path("/mazestatus")
  public Response getMazeStatus(@QueryParam("username") String username) throws MkbException {
    return Response.ok(mapEngine.getMazeStatus(username)).build();
  }

  @GET
  @Path("/resetmaze")
  public Response resetMaze(@QueryParam("username") String username, @QueryParam("id") int id) throws MkbException {
    return Response.ok(maze.reset(username, id)).build();
  }

  @GET
  @Path("/refreshmaze")
  public Response refreshMaze(@QueryParam("username") String username, @QueryParam("id") int id,  @QueryParam("refresh") boolean refresh) throws MkbException {
    return Response.ok(maze.show(username, id, refresh)).build();
  }

  @GET
  @Path("/clearmaze")
  public Response clearMaze(@QueryParam("username") String username, @QueryParam("id") int id, @QueryParam("max") int max) throws MkbException {
    mapEngine.clearMaze(username, id, max);
    return Response.ok(maze.show(username, id, false)).build();
  }

  @GET
  @Path("/counterattacks")
  public Response getCounterAttacks(@QueryParam("username") String username) throws MkbException {
    return Response.ok(mapEngine.findCounterAttackedMapStages(username)).build();
  }

  @POST
  @Path("/clearattacks")
  public Response clearCounterAttacks(ClearCounterAttackRequest request) throws MkbException {
    return Response.ok(new ClearCounterAttackResponse(mapEngine.clearCounterAttackMapStages(request.getUsername(), request.getStageIds(), request.getMaxTry()))).build();
  }

  @GET
  @Path("/attack")
  public Response attackStage(@QueryParam("username") String username, @QueryParam("id") int stageId) throws MkbException {
    mapStage.editUserMapStages(username, stageId);
    return Response.ok(mapStage.getMapStageDetail(username, stageId)).build();
  }
}

