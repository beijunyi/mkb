package im.grusis.mkb.connection.passport.model;

import im.grusis.mkb.connection.passport.model.response.EncryptKeyResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: beij
 * Date: 17/05/13
 * Time: 15:00
 */
public class responseTest {
  @Test
  public void encryptKeyResponseTest() {
    String response = "muhe_encrypt=\"UlwGCB1DCgRfERYSWUYKBVsCRwQDUwUBB1MFDgdWC10AUgAHVwYFDVUEVwZQAgEAUghVAQtWBwECURUDR1wCXxEBFwkVCgMNXxFfAVMEAA9TD1IOC1QHCAxaBA8NUApcBVUDAlAGAQdUC1QAVgUCD0ADFgwCWxdJFllEAgdeCUcEVAMBVwQCAlMHXwZRBgMBWgtUAAVTAgIAVAQIB1EEUAReBAdWABIOGA==\";\n" +
                        "muhe_url=\"http://pp.fantasytoyou.com/pp/userService.do;jsessionid=2bef416a404b0242fc2cdabaefc1;extra=randomProperty\";";
    EncryptKeyResponse model = new EncryptKeyResponse(response);
    Assert.assertEquals(model.getKey(), "UlwGCB1DCgRfERYSWUYKBVsCRwQDUwUBB1MFDgdWC10AUgAHVwYFDVUEVwZQAgEAUghVAQtWBwECURUDR1wCXxEBFwkVCgMNXxFfAVMEAA9TD1IOC1QHCAxaBA8NUApcBVUDAlAGAQdUC1QAVgUCD0ADFgwCWxdJFllEAgdeCUcEVAMBVwQCAlMHXwZRBgMBWgtUAAVTAgIAVAQIB1EEUAReBAdWABIOGA==");
    Assert.assertEquals(model.getUrl(), "http://pp.fantasytoyou.com/pp/userService.do");
    Assert.assertEquals(model.get("jsessionid"), "2bef416a404b0242fc2cdabaefc1");
    Assert.assertEquals(model.get("extra"), "randomProperty");
  }

//  @Test
//  public void serverInformationResponseTest() {
//    String str = "{\"returnCode\":\"0\",\"returnMsg\":\"No error.\",\"returnObjs\":{\"SERVER_DISTRIBUTE\":\"1\",\"GAME_SERVER\":[{\"gsChatIp\":\"116.90.81.150\",\"gsChatPort\":8000,\"gsCreateDate\":1369115980000,\"gsDesc\":\"背主之影\",\"gsId\":1001794437,\"gsIp\":\"http://s11.mysticalcard.com/\",\"gsName\":\"11服—背主之影\",\"gsPlatform\":\"\",\"gsPort\":80,\"gsServiceType\":1,\"gsServiceUrl\":\"http://s11.mysticalcard.com/deposit.php\",\"gsState\":4,\"userCount\":0},{\"gsChatIp\":\"116.90.81.147\",\"gsChatPort\":8000,\"gsCreateDate\":1367979667000,\"gsDesc\":\"邪灵女巫\",\"gsId\":1000651532,\"gsIp\":\"http://s10.mysticalcard.com/\",\"gsName\":\"10服—邪灵女巫\",\"gsPlatform\":\"\",\"gsPort\":80,\"gsServiceType\":1,\"gsServiceUrl\":\"http://s10.mysticalcard.com/deposit.php\",\"gsState\":4,\"userCount\":0}],\"RECOMMENT_SERVER_ID\":\"1001794437\"}}\n";
//    ResponseTemplate r = new ResponseTemplate();
//    r.setResult(str);
//    ServerInformationResponse m = r.getModel();
//    Assert.assertEquals(m.getReturnObjs().getGAME_SERVER().size(), 2);
//  }

}
