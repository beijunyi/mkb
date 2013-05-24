package im.grusis.mkb;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: Mothership
 * Date: 13-5-17
 * Time: 下午8:27
 */
public class unserializerTest {
  @Test
  public void unserializeMapTest() {
    String string = "a:3:{s:1:\"p\";s:39:\"336551506278300215412128472087071431523\";s:1:\"g\";s:38:\"94575518869614444784555444648323179985\";s:1:\"y\";s:38:\"17192470642504943350324972817137676628\";}";
    Map map = Unserializer.UnserializeMap(string);
    Assert.assertEquals(map.get("p"), "336551506278300215412128472087071431523");
    Assert.assertEquals(map.get("g"), "94575518869614444784555444648323179985");
    Assert.assertEquals(map.get("y"), "17192470642504943350324972817137676628");
  }

}
