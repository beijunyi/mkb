package im.grusis.mkb.web.controller;

import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import im.grusis.mkb.web.service.ManualAccessService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * User: Mothership
 * Date: 13-5-26
 * Time: 下午8:15
 */
@Controller
@Path("api/manual")
public class ManualAccessController extends MkbController {
  private static final Logger Log = org.slf4j.LoggerFactory.getLogger(ManualAccessController.class);

  @Autowired
  private ManualAccessService manualAccessService;

  @GET
  @Path("/do/{service}")
  public Response doAction(@PathParam("service") String service, @QueryParam("username") String username,
                              @QueryParam("do") String action, @QueryParam("params") Map<String, String> params) {
    return Response.ok(manualAccessService.doAction(username, service, action, params)).build();
  }

  @GET
  @Path("/login")
  public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
    return Response.ok(manualAccessService.login(username, password)).build();
  }

}
