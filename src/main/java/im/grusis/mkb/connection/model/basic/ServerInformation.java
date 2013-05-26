package im.grusis.mkb.connection.model.basic;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-5-24
 * Time: 下午11:14
 */
public class ServerInformation {
  int SERVER_DISTRIBUTE;
  List<GameServer> GAME_SERVER;
  long RECOMMENT_SERVER_ID;

  public int getSERVER_DISTRIBUTE() {
    return SERVER_DISTRIBUTE;
  }

  public void setSERVER_DISTRIBUTE(int SERVER_DISTRIBUTE) {
    this.SERVER_DISTRIBUTE = SERVER_DISTRIBUTE;
  }

  public List<GameServer> getGAME_SERVER() {
    return GAME_SERVER;
  }

  public void setGAME_SERVER(List<GameServer> GAME_SERVER) {
    this.GAME_SERVER = GAME_SERVER;
  }

  public long getRECOMMENT_SERVER_ID() {
    return RECOMMENT_SERVER_ID;
  }

  public void setRECOMMENT_SERVER_ID(long RECOMMENT_SERVER_ID) {
    this.RECOMMENT_SERVER_ID = RECOMMENT_SERVER_ID;
  }
}
