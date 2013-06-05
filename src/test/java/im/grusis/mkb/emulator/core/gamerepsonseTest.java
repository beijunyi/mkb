package im.grusis.mkb.emulator.core;

import com.google.gson.Gson;
import im.grusis.mkb.emulator.emulator.core.model.response.AllCardResponse;
import org.testng.annotations.Test;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午11:35
 */
public class gamerepsonseTest {

  @Test
  public void cardResponseTest() {
    String str = "{\n" +
                   "    \"status\": 1,\n" +
                   "    \"data\": {\n" +
                   "        \"Cards\": [\n" +
                   "            {\n" +
                   "                \"CardId\": \"1\",\n" +
                   "                \"CardName\": \"人马巡逻者\",\n" +
                   "                \"Cost\": \"3\",\n" +
                   "                \"Color\": \"1\",\n" +
                   "                \"Race\": \"2\",\n" +
                   "                \"Attack\": \"95\",\n" +
                   "                \"Wait\": \"2\",\n" +
                   "                \"Skill\": \"\",\n" +
                   "                \"LockSkill1\": \"23\",\n" +
                   "                \"LockSkill2\": \"64\",\n" +
                   "                \"ImageId\": \"0\",\n" +
                   "                \"FullImageId\": \"0\",\n" +
                   "                \"Price\": \"300\",\n" +
                   "                \"BaseExp\": \"50\",\n" +
                   "                \"SeniorPacket\": \"1\",\n" +
                   "                \"MasterPacket\": \"0\",\n" +
                   "                \"Maze\": \"1\",\n" +
                   "                \"Robber\": \"1\",\n" +
                   "                \"MagicCard\": \"0\",\n" +
                   "                \"Boss\": \"0\",\n" +
                   "                \"BossCounter\": \"0\",\n" +
                   "                \"FactionCounter\": \"0\",\n" +
                   "                \"Glory\": \"0\",\n" +
                   "                \"FightMPacket\": \"0\",\n" +
                   "                \"HpArray\": [\n" +
                   "                    310,\n" +
                   "                    321,\n" +
                   "                    332,\n" +
                   "                    343,\n" +
                   "                    354,\n" +
                   "                    365,\n" +
                   "                    376,\n" +
                   "                    387,\n" +
                   "                    398,\n" +
                   "                    409,\n" +
                   "                    420\n" +
                   "                ],\n" +
                   "                \"AttackArray\": [\n" +
                   "                    95,\n" +
                   "                    112,\n" +
                   "                    129,\n" +
                   "                    146,\n" +
                   "                    163,\n" +
                   "                    180,\n" +
                   "                    197,\n" +
                   "                    214,\n" +
                   "                    231,\n" +
                   "                    248,\n" +
                   "                    265\n" +
                   "                ],\n" +
                   "                \"ExpArray\": [\n" +
                   "                    \"0\",\n" +
                   "                    \"60\",\n" +
                   "                    \"190\",\n" +
                   "                    \"380\",\n" +
                   "                    \"640\",\n" +
                   "                    \"1150\",\n" +
                   "                    \"1790\",\n" +
                   "                    \"2590\",\n" +
                   "                    \"3550\",\n" +
                   "                    \"4670\",\n" +
                   "                    \"6400\"\n" +
                   "                ]\n" +
                   "            }\n" +
                   "        ]\n" +
                   "    },\n" +
                   "    \"version\": {\n" +
                   "        \"http\": \"201302078\",\n" +
                   "        \"stop\": \"\",\n" +
                   "        \"appversion\": \"version_1\",\n" +
                   "        \"appurl\": \"ios://xxx\"\n" +
                   "    },\n" +
                   "    \"$resolved\": true\n" +
                   "}";
//    Map map = new Gson().fromJson(str, Map.class);
    AllCardResponse acr = new Gson().fromJson(str, AllCardResponse.class);
    System.currentTimeMillis();
  }
}
