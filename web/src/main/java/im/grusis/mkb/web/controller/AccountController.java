package im.grusis.mkb.web.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

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

  @GET
  @Path("/login")
  public Response login(@QueryParam("username") String username, @QueryParam("password") String password) throws MkbException {
    MkbAccount account = accountService.findAccountByUsername(username);
    if(account == null || (!account.getPassword().equals(password) && !password.isEmpty())) {
      emulator.webLogin(username, password);
      emulator.gamePassportLogin(username);
    }
    return Response.ok(emulator.gameGetUserInfo(username, false)).build();
  }

}
