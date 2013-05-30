package im.grusis.mkb;

import im.grusis.mkb.config.RuntimeConfig;
import im.grusis.mkb.emulator.MkbEmulator;
import im.grusis.mkb.emulator.passport.model.request.RegUserRequest;
import im.grusis.mkb.emulator.passport.model.response.LoginInformationResponse;
import im.grusis.mkb.util.MacAddressHelper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.Test;

/**
 * User: Mothership
 * Date: 13-5-18
 * Time: 下午7:43
 */
public class TEMP {
  @Test
  public void T() {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(RuntimeConfig.class);
    ctx.start();
    MkbEmulator emulator = ctx.getBean(MkbEmulator.class);
//    ServerInformationResponse serverInformationResponse = emulator.passportRequest(new ServerRequest(), ServerInformationResponse.class);

        emulator.passportRequest(new RegUserRequest("mewtester4", "123456", 1001794437, MacAddressHelper.getMacAddress()), LoginInformationResponse.class);
    System.out.println(System.currentTimeMillis());


//    DefaultHttpClient httpClient = new DefaultHttpClient();
//    LoginInformation li = PassportHelper.request(new LoginRequest("mewrobot0001", "123456", null), LoginInformationResponse.class, httpClient).getReturnObjs();
//    MkbCore core = new MkbCore(li.getGS_IP(), li.getUserName(), li.getU_ID(), li.getKey(), MacAddressHelper.getMacAddress(), li.getTimestamp(), httpClient);
  }
}
