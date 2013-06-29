package im.grusis.mkb.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import javax.ws.rs.*;
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

  private Map<String, AccountBotProgress> progresses = new HashMap<String, AccountBotProgress>();


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
  public Response produceAccounts(AccountBotSettings accountBotSettings) {
    AccountBotProgress progress = new AccountBotProgress(accountBotSettings.getTotal());
    String uuid = UUID.randomUUID().toString();
    progresses.put(uuid, progress);
    executorService.submit(botService.newAccountBot(accountBotSettings, progress));
    return Response.ok(uuid).build();
  }

  @GET
  @Path("/update")
  public Response getProductionUpdate(@QueryParam("uuid") String uuid) {
    AccountBotProgress progress = progresses.get(uuid);
    if(progress.isFinish()) {
      progresses.remove(uuid);
    }
    return Response.ok(progress).build();
  }



}
