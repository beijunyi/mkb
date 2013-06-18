package im.grusis.mkb.core.emulator.game.model.basic;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-6-7
 * Time: 下午10:06
 */
public class MapStage {
 private int MapStageId;
 private String Name;
 private int Count;
 private int EveryDayReward;
 private int Rank;
 private int MazeCount;
 private int NeedStar;
 private int Prev;
 private int Next;
 private List<MapStageDetail> MapStageDetails;

  public int getMapStageId() {
    return MapStageId;
  }

  public void setMapStageId(int mapStageId) {
    MapStageId = mapStageId;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public int getCount() {
    return Count;
  }

  public void setCount(int count) {
    Count = count;
  }

  public int getEveryDayReward() {
    return EveryDayReward;
  }

  public void setEveryDayReward(int everyDayReward) {
    EveryDayReward = everyDayReward;
  }

  public int getRank() {
    return Rank;
  }

  public void setRank(int rank) {
    Rank = rank;
  }

  public int getMazeCount() {
    return MazeCount;
  }

  public void setMazeCount(int mazeCount) {
    MazeCount = mazeCount;
  }

  public int getNeedStar() {
    return NeedStar;
  }

  public void setNeedStar(int needStar) {
    NeedStar = needStar;
  }

  public int getPrev() {
    return Prev;
  }

  public void setPrev(int prev) {
    Prev = prev;
  }

  public int getNext() {
    return Next;
  }

  public void setNext(int next) {
    Next = next;
  }

  public List<MapStageDetail> getMapStageDetails() {
    return MapStageDetails;
  }

  public void setMapStageDetails(List<MapStageDetail> mapStageDetails) {
    MapStageDetails = mapStageDetails;
  }
}
