package im.grusis.mkb;

import im.grusis.mkb.config.RuntimeConfig;
import im.grusis.mkb.emulator.bot.BotManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.Test;

/**
 * User: Mothership
 * Date: 13-5-18
 * Time: 下午7:43
 */
public class TEMP {
  @Test
  public void T() throws Exception {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(RuntimeConfig.class);
    ctx.start();

    BotManager bm = ctx.getBean(BotManager.class);
    bm.angelBot("mkbangel", "使徒", 1, 331, "45ekat");

//    MkbEmulator emulator = ctx.getBean(MkbEmulator.class);
//
//    String username = "mewtester23";
//    String password = "123456";
//    String invite = "45ekat";
//    String mac = MacAddressHelper.getMacAddress();
//    String nickname = "天使机器19";
//    emulator.webReg(username, password, mac, 331);
//    emulator.webLogin(username);
//    emulator.gamePassportLogin(username);
//    emulator.gameSetNickname(username, 1, invite, nickname);
//    emulator.gamePurchase(username, 3);
//    emulator.gameSkipTutorial(username, "1_21");
//    emulator.gameSkipTutorial(username, "2_10");
//    emulator.gameSkipTutorial(username, "2_17");

    ////    ServerInformationResponse serverInformationResponse = emulator.passportRequest(new ServerRequest(), ServerInformationResponse.class);
    //

    //    String mac = MacAddressHelper.getMacAddress();
    //    StringResponse sr = emulator.passportRequest(new RegUserRequest(username, password, 331, MacAddressHelper.getMacAddress()), StringResponse.class);
    //    LoginInformation li = emulator.passportRequest(new LoginRequest(username, password, mac), LoginInformationResponse.class).getReturnObjs();
    //    MkbCore core = emulator.getMkbCore(li.getGS_IP(), li.getUserName(), li.getU_ID(), li.getKey(), MacAddressHelper.getMacAddress(), li.getTimestamp());
    ////    MkbCore core = emulator.getMkbCore("http://s6.mysticalcard.com/", username, 0, null, MacAddressHelper.getMacAddress(), 0);
    //    PassportLogin pl = core.doPassportLogin();
    //    Map<String, String> params = new LinkedHashMap<String, String>();
    //    params.put("Sex", "1");
    //    params.put("InviteCode", invite);
    //    params.put("NickName", "天使机器05");
    //    String da = core.doAction("user.php", "EditNickName", params);
    //
    //    Map<String, String> params2 = new LinkedHashMap<String, String>();
    //    params2.put("GoodsId", "3");
    //    String da2 = core.doAction("shop.php", "Buy", params2);

    //    File file = new File("e:\\rec");
    //    String[] a = file.list();
    System.out.println(System.currentTimeMillis());

    //    DefaultHttpClient httpClient = new DefaultHttpClient();
    //    LoginInformation li = PassportHelper.request(new LoginRequest("mewrobot0001", "123456", null), LoginInformationResponse.class, httpClient).getReturnObjs();
    //    MkbCore core = new MkbCore(li.getGS_IP(), li.getUserName(), li.getU_ID(), li.getKey(), MacAddressHelper.getMacAddress(), li.getTimestamp(), httpClient);
  }
}
