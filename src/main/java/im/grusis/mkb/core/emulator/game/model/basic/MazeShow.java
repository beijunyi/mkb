package im.grusis.mkb.core.emulator.game.model.basic;

/**
 * User: Mothership
 * Date: 13-6-7
 * Time: 下午11:15
 */
public class MazeShow {
  private String Name;
  private int Layer;
  private int Clear;
  private int FreeReset;
  private int ResetCash;

  public boolean clear() {
    return Clear != 0;
  }

  public boolean freeReset() {
    return FreeReset != 0;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public int getLayer() {
    return Layer;
  }

  public void setLayer(int layer) {
    Layer = layer;
  }

  public int getClear() {
    return Clear;
  }

  public void setClear(int clear) {
    Clear = clear;
  }

  public int getFreeReset() {
    return FreeReset;
  }

  public void setFreeReset(int freeReset) {
    FreeReset = freeReset;
  }

  public int getResetCash() {
    return ResetCash;
  }

  public void setResetCash(int resetCash) {
    ResetCash = resetCash;
  }
}
