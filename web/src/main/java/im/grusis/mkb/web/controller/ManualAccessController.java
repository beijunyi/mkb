package im.grusis.mkb.web.controller;

import java.lang.reflect.Type;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.service.ManualAccessService;
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
@Produces(MediaType.APPLICATION_JSON)
public class ManualAccessController {
  private static final Logger Log = org.slf4j.LoggerFactory.getLogger(ManualAccessController.class);

  @Autowired
  private ManualAccessService manualAccessService;

  @GET
  @Path("/do/{service}")
  public Response doAction(@PathParam("service") String service, @QueryParam("username") String username, @QueryParam("do") String action, @QueryParam("params") String params) {
    try {
      Type type = new TypeToken<Map<String, String>>() {
      }.getType();
      Map<String, String> paramMap = new Gson().fromJson(params, type);
      String responseString = manualAccessService.doAction(username, service, action, paramMap);
      try {
        return Response.ok(new Gson().fromJson(responseString, Object.class)).build();
      } catch(Exception e) {
        return Response.ok(responseString).build();
      }
    } catch(Exception e) {
      Log.error("Cannot perform action {}?do={} for ", service, action, username);
      return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
    }

  }

  @GET
  @Path("/login")
  public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
    try {
      return Response.ok(manualAccessService.login(username, password)).build();
    } catch(Exception e) {
      Log.error("Cannot perform login for {}", username);
      return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
    }
  }

  @GET
  @Path("/getUserInfo")
  public Response getUserInfo(@QueryParam("username") String username) throws ServerNotAvailableException, UnknownErrorException {
    try {
      return Response.ok(manualAccessService.getUserInfo(username)).build();
    } catch(Exception e) {
      Log.error("Cannot perform getUserInfo for {}", username);
      return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
    }
  }

}
