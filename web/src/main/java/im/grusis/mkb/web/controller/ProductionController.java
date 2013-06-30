package im.grusis.mkb.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import im.grusis.mkb.core.service.AssetsService;
import im.grusis.mkb.eco.bot.model.AccountBotProgress;
import im.grusis.mkb.eco.bot.model.AccountBotSettings;
import im.grusis.mkb.eco.service.BotService;
import im.grusis.mkb.eco.service.ProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * User: Mothership
 * Date: 13-6-25
 * Time: 下午5:25
 */
@Controller
@Path("api/production")
public class ProductionController {

  private Map<HttpSession, AccountBotProgress> progresses = new HashMap<HttpSession, AccountBotProgress>();


  @Autowired ProductionService productionService;
  @Autowired AssetsService assetsService;
  @Autowired ExecutorService executorService;
  @Autowired BotService botService;

  @GET
  @Path("/criterion")
  public Response getProductionCriterion() {
    return Response.ok(productionService.getProductionCriterion()).build();
  }

  @POST
  @Path("/addcriteria")
  public Response addCriteria(Map<Integer, Integer> cardCount) {
    return Response.ok(productionService.addProductionCriteria(cardCount)).build();
  }

  @GET
  @Path("/removecriteria")
  public Response removeCriteria(@QueryParam("id") int id) {
    return Response.ok(productionService.removeProductionCriteria(id)).build();
  }

  @GET
  @Path("/editcomment")
  public Response editcomment(@QueryParam("id") int id, @QueryParam("comment") String comment) {
    return Response.ok(productionService.editProductionCriteriaComment(id, comment)).build();
  }

  @GET
  @Path("/servers")
  public Response getServers() {
    return Response.ok(assetsService.getGameServerLookup()).build();
  }

  @POST
  @Path("/produce")
  public Response produceAccounts(AccountBotSettings accountBotSettings, @Context HttpServletRequest httpRequest) {
    HttpSession session = httpRequest.getSession();
    AccountBotProgress progress = new AccountBotProgress(accountBotSettings.getTotal());
    progresses.put(session, progress);
    executorService.submit(botService.newAccountBot(accountBotSettings, progress));
    return Response.ok().build();
  }

  @GET
  @Path("/update")
  public Response getProductionUpdate(@Context HttpServletRequest httpRequest) {
    HttpSession session = httpRequest.getSession();
    AccountBotProgress progress = progresses.get(session);
    if(progress.isFinish()) {
      progresses.remove(session);
    }
    return Response.ok(progress).build();
  }



}
