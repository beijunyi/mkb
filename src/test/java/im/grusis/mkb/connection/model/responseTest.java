package im.grusis.mkb.connection.model;

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
    Assert.assertEquals(model.getKeylen(), 128);
    Assert.assertEquals(model.get("jsessionid"), "2bef416a404b0242fc2cdabaefc1");
    Assert.assertEquals(model.get("extra"), "randomProperty");
  }
}
