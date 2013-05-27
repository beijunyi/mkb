package im.grusis.mkb.web.controller;

import javax.ws.rs.Path;

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

}
