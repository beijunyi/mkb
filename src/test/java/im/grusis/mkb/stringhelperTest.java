package im.grusis.mkb;

import org.testng.Assert;
import org.testng.annotations.Test;
import im.grusis.mkb.util.StringHelper;

/**
 * User: Mothership
 * Date: 13-5-24
 * Time: 下午9:43
 */
public class stringhelperTest {

  @Test
  public void testToUTF8() {
    String str = "AS背主之影ASD";
    Assert.assertEquals(StringHelper.toUTF8(str), "ASè\u0083\u008Cä¸»ä¹\u008Bå½±ASD");
  }

  @Test
  public void testToUTF16() {
    String str = "1è\u0083\u008Cä¸»ä¹\u008Bå½±L";
    Assert.assertEquals(StringHelper.toUTF16(str), "1背主之影L");
  }
}
