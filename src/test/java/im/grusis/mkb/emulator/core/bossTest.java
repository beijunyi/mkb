package im.grusis.mkb.emulator.core;

import com.google.gson.Gson;
import im.grusis.mkb.emulator.emulator.core.model.response.BossGetBossResponse;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * User: Mothership
 * Date: 13-6-15
 * Time: 下午6:25
 */
public class bossTest {

  @Test
  public void bossUpdate() {
    String json = "{\"status\":1,\"data\":{\"Boss\":{\"BossCardId\":\"9006\",\"BossCardLevel\":10,\"FeebleCardId\":[\"16\",\"69\"],\"WinAward\":{\"AwardType\":2,\"AwardValue\":300},\"HonorAwards\":[{\"Honor\":1,\"AwardType\":1,\"AwardValue\":5000,\"EndHonor\":4999},{\"Honor\":5000,\"AwardType\":1,\"AwardValue\":10000,\"EndHonor\":9999},{\"Honor\":10000,\"AwardType\":1,\"AwardValue\":15000,\"EndHonor\":14999},{\"Honor\":15000,\"AwardType\":1,\"AwardValue\":20000,\"EndHonor\":19999},{\"Honor\":20000,\"AwardType\":1,\"AwardValue\":25000,\"EndHonor\":27999},{\"Honor\":28000,\"AwardType\":1,\"AwardValue\":30000,\"EndHonor\":39999},{\"Honor\":40000,\"AwardType\":1,\"AwardValue\":40000,\"EndHonor\":59999},{\"Honor\":60000,\"AwardType\":1,\"AwardValue\":50000,\"EndHonor\":79999},{\"Honor\":80000,\"AwardType\":1,\"AwardValue\":60000,\"EndHonor\":99999},{\"Honor\":100000,\"AwardType\":1,\"AwardValue\":70000,\"EndHonor\":119999},{\"Honor\":120000,\"AwardType\":1,\"AwardValue\":80000,\"EndHonor\":149999},{\"Honor\":150000,\"AwardType\":1,\"AwardValue\":100000,\"EndHonor\":199999},{\"Honor\":200000,\"AwardType\":1,\"AwardValue\":120000,\"EndHonor\":0}],\"Times\":[[\"13\",\"15\"],[\"21\",\"23\"]],\"RankAwards\":[{\"StartRank\":1,\"EndRank\":1,\"AwardType\":4,\"AwardValue\":\"90\"},{\"StartRank\":2,\"EndRank\":2,\"AwardType\":2,\"AwardValue\":518},{\"StartRank\":3,\"EndRank\":3,\"AwardType\":2,\"AwardValue\":318},{\"StartRank\":4,\"EndRank\":4,\"AwardType\":2,\"AwardValue\":218},{\"StartRank\":5,\"EndRank\":5,\"AwardType\":2,\"AwardValue\":118},{\"StartRank\":6,\"EndRank\":6,\"AwardType\":3,\"AwardValue\":5},{\"StartRank\":7,\"EndRank\":7,\"AwardType\":3,\"AwardValue\":4},{\"StartRank\":8,\"EndRank\":8,\"AwardType\":3,\"AwardValue\":3},{\"StartRank\":9,\"EndRank\":9,\"AwardType\":3,\"AwardValue\":2},{\"StartRank\":10,\"EndRank\":10,\"AwardType\":3,\"AwardValue\":2}],\"RankEnd\":50,\"HonorEnd\":1,\"JoinAward\":{\"AwardType\":1,\"AwardValue\":5000},\"FeebleCardIds\":[9,13,14,15,16,17,18,19,20,21,22,36,42,44,45,46,47,48,49,50,51,52,69,73,74,75,76,77,78,79,80,81,82,101,103,104,105,106,107,108,109,110,111,112],\"BossHp\":19200000,\"BossCurrentHp\":18126938},\"MyHonor\":2618,\"CanFightTime\":295,\"BossFleeTime\":7169},\"version\":{\"http\":\"201302090\",\"stop\":\"\",\"appversion\":\"version_1\",\"appurl\":\"ios:\\/\\/xxx\"}}";
    BossGetBossResponse response = new Gson().fromJson(json, BossGetBossResponse.class);
    Assert.assertNotNull(response);
  }
}
