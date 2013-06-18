package im.grusis.mkb;

import org.testng.Assert;
import org.testng.annotations.Test;
import im.grusis.mkb.core.util.Encoder;
import im.grusis.mkb.core.util.XXTEA;

/**
 * User: Mothership
 * Date: 13-5-16
 * Time: 下午11:33
 */
public class encoderTest {
  @Test
  public void btoaTest() {
    String value = "aaa";
    String encoded = Encoder.btoa(value);
    Assert.assertEquals("YWFh", encoded);
    Assert.assertEquals(value, Encoder.atob(encoded));

    value = "akshjdoakjsgdujabsdjuiawgduiaghsduiahpsiudhasdaosdjaiodsa";
    encoded = Encoder.btoa(value);
    Assert.assertEquals("YWtzaGpkb2FranNnZHVqYWJzZGp1aWF3Z2R1aWFnaHNkdWlhaHBzaXVkaGFzZGFvc2RqYWlvZHNh", encoded);

    value = XXTEA.intArrayToString(new int[]{-53005218, -1118378643, 604457902, 2110967903, 647961658, -1292698266, -1071120392, -403624324, -1238995454, -1610035750, -836198715, -765298039, -1464365091, -223171722, -704031125, -1716140430, 1288265131, -900159573, 1251391155, 2109106364, -1770424730, 872800869, 1284954290, 529511037, -628713092, -1938482725, -427868340, -1413181674, 488896573, 1695393850, 1657071169, 562730169, 966190073, 783904881, 1390549283, 1060706766, -814409709, 836569375, 1032534718, -260957538, -662849611, 1693177579, -2012665223, 172}, true);
    encoded = Encoder.btoa(value);
    Assert.assertEquals("XjTX/G3lVr2uSwckX9DSfTocnyZm/fKy+P8nwHwu8ecCbia22s0IoMWeKM6JemLS3Y+3qHarsvJrVgnWcsa1matdyUyrp1jKs7aWSrxotn1mdnmWZeIFNLLYlkx9so8ffJmG2tsZdYxMP3/mFo/Eqz34Ix06qA1lQebEYrmUiiH545Y5cXC5LiMZ4lLOGTk/Exh1zx8J3TG+Oos9nhpy8LW3fdjr1utkeSoJiA==", encoded);
  }

  @Test
  public void atobTest() {
    String value = "UlwGCB1DCgRfERYSWUYKBVsCRwcEVA0JB1QACwBUC1IAVQQLVwgBBlILUgRaBAkPWg5UDgBTDQIAURUDR1wCXxEBFwkVCgMMXxFXA1ANBAdUC1EFA1YACQVQBwkAVABUBlIBA1AJBQNQBF8EUAwBA1UaXkUJUA8STUAMSw5VBV8RVAcEUAYBBFILVgNaBAYPVwlUBgNQAQUEVwQLB1ELUQdVAQRfEgtI";
    String decoded = Encoder.atob(value);
    Assert.assertEquals(new int[] {134634578, 67781405, 303436127, 84559449, 122094171, 151868420, 184570887, 1376474112, 184833280, 100730967, 72485714, 252249178, 240389722, 34427648, 51728640, 1593990215, 152502545, 201525781, 56037727, 117706064, 89197396, 151016963, 151474181, 1409307648, 50418182, 50661712, 73335888, 50400336, 1163795029, 302993417, 1259094093, 1594184974, 67589137, 67176016, 55970642, 252052570, 106170711, 83972099, 184833796, 1359696135, 67196167, 1208685151, 168}, XXTEA.stringToIntArray(decoded, true));
  }

}
