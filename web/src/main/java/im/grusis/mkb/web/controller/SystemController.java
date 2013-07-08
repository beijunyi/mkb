package im.grusis.mkb.web.controller;

import java.util.*;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import im.grusis.mkb.core.emulator.EmulatorUser;
import im.grusis.mkb.core.emulator.EmulatorWeb;
import im.grusis.mkb.core.emulator.game.model.basic.UserInfo;
import im.grusis.mkb.core.emulator.web.model.basic.GameServer;
import im.grusis.mkb.core.exception.MkbException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import im.grusis.mkb.core.service.ArchiveService;
import im.grusis.mkb.core.util.AccountFilter;
import im.grusis.mkb.eco.service.EcoSettingsService;
import im.grusis.mkb.eco.util.filter.common.*;
import im.grusis.mkb.eco.util.filter.operators.AndFilter;
import im.grusis.mkb.web.model.AccountView;
import im.grusis.mkb.web.model.FindAccountRequest;
import im.grusis.mkb.web.model.FindAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * User: Mothership
 * Date: 13-6-25
 * Time: 下午5:49
 */
@Controller
@Path("api/system")
public class SystemController {

  @Autowired AccountService accountService;
  @Autowired EmulatorUser user;
  @Autowired EmulatorWeb web;
  @Autowired ArchiveService archiveService;
  @Autowired EcoSettingsService ecoSettingsService;

  private AccountFilter createFilter(List<Map<String, String>> filterMap) {
    List<AccountFilter> ret = new ArrayList<AccountFilter>();
    Map<Integer, Integer> cardCount = new HashMap<Integer, Integer>();
    for(Map<String, String> f : filterMap) {
      String t = f.get("type");
      if(t.equals("level")) {
        ret.add(new NumericPropertyFilter(NumericProperty.Level, CompareOperator.valueOf(f.get("compare")), Long.valueOf(f.get("value"))));
      } else if(t.equals("card")) {
        int id = Integer.parseInt(f.get("id"));
        int num = Integer.parseInt(f.get("number"));
        Integer count = cardCount.get(id);
        if(count == null) {
          cardCount.put(id, num);
        } else {
          if(count > num) {
            num = count;
          }
          cardCount.put(id, num);
        }
      } else if(t.equals("currency")) {
        ret.add(new NumericPropertyFilter(NumericProperty.valueOf(f.get("currency")), CompareOperator.valueOf(f.get("compare")), Long.valueOf(f.get("value"))));
      } else if(t.equals("legion")) {
        ret.add(new LegionNameFilter(f.get("name")));
      }
    }
    if(!cardCount.isEmpty()) {
      ret.add(new CardNumberFilter(CompareOperator.GreaterThanOrEqualTo, cardCount));
    }
    return new AndFilter(ret);
  }

  @POST
  @Path("/accounts")
  public Response findAccounts(FindAccountRequest request) throws MkbException {
    List<AccountView> ret = new ArrayList<AccountView>();
    Map<String, GameServer> servers = web.getServers(false);
    for(MkbAccount a : accountService.findAll(createFilter(request.getFilters()))) {
      String un = a.getUsername();
      UserInfo ui = user.getUserInfo(un, false);
      ret.add(new AccountView(servers.get(a.getServer()).getGsName(), un, ui.getNickName(), ui.getLevel()));
    }
    return Response.ok(new FindAccountResponse(ret)).build();
  }

  @GET
  @Path("/boss")
  public Response getBossPool() {
    return Response.ok(ecoSettingsService.getBossPool()).build();
  }

  @GET
  @Path("/maze")
  public Response getMazePool() {
    return Response.ok(ecoSettingsService.getMazePool()).build();
  }

  @GET
  @Path("/map")
  public Response getMapPool() {
    return Response.ok(ecoSettingsService.getMapPool()).build();
  }

  @GET
  @Path("/fenergy")
  public Response getFenergyPool() {
    return Response.ok(ecoSettingsService.getFenergyPool()).build();
  }

  @GET
  @Path("/friends")
  public Response getFriendsPool() {
    return Response.ok(ecoSettingsService.getFriendsPool()).build();
  }

  @GET
  @Path("/legion")
  public Response getLegionPool() {
    return Response.ok(ecoSettingsService.getLegionPool()).build();
  }




}
