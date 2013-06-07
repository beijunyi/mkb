package im.grusis.mkb.emulator.emulator.core.model.basic;

/**
 * User: Mothership
 * Date: 13-6-7
 * Time: 下午11:07
 */
public class MazeInfo {
  private String Name;
  private int BoxNum;
  private int MonsterNum;
  private int RemainBoxNum;
  private int RemainMonsterNum;
  private int Layer;
  private int TotalLayer;
  private MazeMap Map;

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public int getBoxNum() {
    return BoxNum;
  }

  public void setBoxNum(int boxNum) {
    BoxNum = boxNum;
  }

  public int getMonsterNum() {
    return MonsterNum;
  }

  public void setMonsterNum(int monsterNum) {
    MonsterNum = monsterNum;
  }

  public int getRemainBoxNum() {
    return RemainBoxNum;
  }

  public void setRemainBoxNum(int remainBoxNum) {
    RemainBoxNum = remainBoxNum;
  }

  public int getRemainMonsterNum() {
    return RemainMonsterNum;
  }

  public void setRemainMonsterNum(int remainMonsterNum) {
    RemainMonsterNum = remainMonsterNum;
  }

  public int getLayer() {
    return Layer;
  }

  public void setLayer(int layer) {
    Layer = layer;
  }

  public int getTotalLayer() {
    return TotalLayer;
  }

  public void setTotalLayer(int totalLayer) {
    TotalLayer = totalLayer;
  }

  public MazeMap getMap() {
    return Map;
  }

  public void setMap(MazeMap map) {
    Map = map;
  }

  public class MazeMap {
    private boolean IsFinish;
    private int[] WallRows;
    private int[] WallCols;
    private int[] Items;

    public boolean isIsFinish() {
      return IsFinish;
    }

    public void setIsFinish(boolean isFinish) {
      IsFinish = isFinish;
    }

    public int[] getWallRows() {
      return WallRows;
    }

    public void setWallRows(int[] wallRows) {
      WallRows = wallRows;
    }

    public int[] getWallCols() {
      return WallCols;
    }

    public void setWallCols(int[] wallCols) {
      WallCols = wallCols;
    }

    public int[] getItems() {
      return Items;
    }

    public void setItems(int[] items) {
      Items = items;
    }
  }
}
