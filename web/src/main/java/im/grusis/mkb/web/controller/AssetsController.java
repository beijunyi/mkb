package im.grusis.mkb.web.controller;

import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import im.grusis.mkb.core.emulator.MkbEmulator;
import im.grusis.mkb.core.emulator.game.model.basic.*;
import im.grusis.mkb.core.exception.MkbException;
import im.grusis.mkb.core.service.AssetsService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * User: Mothership
 * Date: 13-6-22
 * Time: 下午12:38
 */
@Controller
@Path("api/assets")
public class AssetsController {
  private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(AssetsController.class);

  @Autowired MkbEmulator emulator;
  @Autowired AssetsService assetsService;

  @GET
  @Path("/refresh")
  public Response refreshAssets(@QueryParam("username") String username) throws MkbException {
    emulator.gameGetMapDefs(username, true);
    emulator.gameGetSkills(username, true);
    emulator.gameGetCards(username, true);
    emulator.gameGetRunes(username, true);
    emulator.gameShopGetGoodsList(username, true);
    return Response.ok().build();
  }

  @GET
  @Path("/maps")
  public Response getMapDefs() {
    Map<Integer, MapDef> maps = assetsService.getMapStageLookup();
    return Response.ok(maps).build();
  }

 @GET
  @Path("/mapstages")
  public Response getMapStageDefs() {
    Map<Integer, MapStageDef> stages = assetsService.getMapStageDetailLookup();
    return Response.ok(stages).build();
  }

  @GET
  @Path("/skills")
  public Response getSkillDefs() {
    Map<Integer, SkillDef> skills = assetsService.getSkillLookup();
    return Response.ok(skills).build();
  }

  @GET
  @Path("/cards")
  public Response getCardDefs() {
    Map<Integer, CardDef> cards = assetsService.getCardLookup();
    return Response.ok(cards).build();
  }

  @GET
  @Path("/runes")
  public Response getRuneDefs() {
    Map<Integer, RuneDef> runes = assetsService.getRuneLookup();
    return Response.ok(runes).build();
  }

  @GET
  @Path("/goods")
  public Response getGoodsDefs() {
    Map<Integer, Goods> goods = assetsService.getGoodsLookup();
    return Response.ok(goods).build();
  }

}
