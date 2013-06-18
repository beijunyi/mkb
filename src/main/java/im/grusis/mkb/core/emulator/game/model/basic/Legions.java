package im.grusis.mkb.core.emulator.game.model.basic;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-6-12
 * Time: 下午11:13
 */
public class Legions {
  private Legion MyLegion;
  private Member MyInfo;
  private List<Legion> LegionInfos;
  private int Count;

  public Legion getMyLegion() {
    return MyLegion;
  }

  public void setMyLegion(Legion myLegion) {
    MyLegion = myLegion;
  }

  public Member getMyInfo() {
    return MyInfo;
  }

  public void setMyInfo(Member myInfo) {
    MyInfo = myInfo;
  }

  public List<Legion> getLegionInfos() {
    return LegionInfos;
  }

  public void setLegionInfos(List<Legion> legionInfos) {
    LegionInfos = legionInfos;
  }

  public int getCount() {
    return Count;
  }

  public void setCount(int count) {
    Count = count;
  }
}
