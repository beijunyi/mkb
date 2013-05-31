package im.grusis.mkb;

import java.util.LinkedHashMap;
import java.util.Map;

import im.grusis.mkb.config.RuntimeConfig;
import im.grusis.mkb.emulator.emulator.MkbEmulator;
import im.grusis.mkb.emulator.emulator.core.MkbCore;
import im.grusis.mkb.emulator.emulator.core.model.basic.PassportLogin;
import im.grusis.mkb.emulator.emulator.passport.model.basic.LoginInformation;
import im.grusis.mkb.emulator.emulator.passport.model.request.LoginRequest;
import im.grusis.mkb.emulator.emulator.passport.model.request.RegUserRequest;
import im.grusis.mkb.emulator.emulator.passport.model.response.LoginInformationResponse;
import im.grusis.mkb.emulator.emulator.passport.model.response.StringResponse;
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
    ////    ServerInformationResponse serverInformationResponse = emulator.passportRequest(new ServerRequest(), ServerInformationResponse.class);
    //
    String username = "mewtester7";
    String password = "123456";
    String invite = "45ejwe";
    String mac = MacAddressHelper.getMacAddress();
    StringResponse sr = emulator.passportRequest(new RegUserRequest(username, password, 331, MacAddressHelper.getMacAddress()), StringResponse.class);
    LoginInformation li = emulator.passportRequest(new LoginRequest(username, password, mac), LoginInformationResponse.class).getReturnObjs();
    MkbCore core = emulator.getMkbCore(li.getGS_IP(), li.getUserName(), li.getU_ID(), li.getKey(), MacAddressHelper.getMacAddress(), li.getTimestamp());
    PassportLogin pl = core.doPassportLogin();
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("Sex", "1");
    params.put("InviteCode", invite);
    params.put("NickName", "天使机器03");
    String da = core.doAction("user.php", "EditNickName", params);

    Map<String, String> params2 = new LinkedHashMap<String, String>();
    params2.put("GoodsId", "3");
    String da2 = core.doAction("shop.php", "Buy", params2);


    //    File file = new File("e:\\rec");
    //    String[] a = file.list();
    System.out.println(System.currentTimeMillis());

    //    DefaultHttpClient httpClient = new DefaultHttpClient();
    //    LoginInformation li = PassportHelper.request(new LoginRequest("mewrobot0001", "123456", null), LoginInformationResponse.class, httpClient).getReturnObjs();
    //    MkbCore core = new MkbCore(li.getGS_IP(), li.getUserName(), li.getU_ID(), li.getKey(), MacAddressHelper.getMacAddress(), li.getTimestamp(), httpClient);
  }
}
