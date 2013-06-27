package im.grusis.mkb.web.controller;

import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

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

  @Autowired ProductionService productionService;

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

}
