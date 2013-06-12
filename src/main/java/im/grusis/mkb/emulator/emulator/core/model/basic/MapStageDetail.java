package im.grusis.mkb.emulator.emulator.core.model.basic;

import java.util.List;

/**
 * User: Mothership
 * Date: 13-6-7
 * Time: 下午9:47
 */
public class MapStageDetail {
  public static final int NormalLevel = 1;
  public static final int BossLevel = 2;
  public static final int SecretLevel = 0;
  public static final int MazeLevel = 3;

  private int MapStageDetailId;
  private String Name;
  private int Type;
  private int Rank;
  private int X;
  private int Y;
  private int Prev;
  private int Next;
  private int NextBranch;
  private String FightName;
  private String FightImg;
  private List<Dialogue> Dialogue;
  private List<Dialogue> DialogueAfter;
  private List<Level> Levels;

  public int getMapStageDetailId() {
    return MapStageDetailId;
  }

  public void setMapStageDetailId(int mapStageDetailId) {
    MapStageDetailId = mapStageDetailId;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public int getType() {
    return Type;
  }

  public void setType(int type) {
    Type = type;
  }

  public int getRank() {
    return Rank;
  }

  public void setRank(int rank) {
    Rank = rank;
  }

  public int getX() {
    return X;
  }

  public void setX(int x) {
    X = x;
  }

  public int getY() {
    return Y;
  }

  public void setY(int y) {
    Y = y;
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

  public int getNextBranch() {
    return NextBranch;
  }

  public void setNextBranch(int nextBranch) {
    NextBranch = nextBranch;
  }

  public String getFightName() {
    return FightName;
  }

  public void setFightName(String fightName) {
    FightName = fightName;
  }

  public String getFightImg() {
    return FightImg;
  }

  public void setFightImg(String fightImg) {
    FightImg = fightImg;
  }

  public List<Dialogue> getDialogue() {
    return Dialogue;
  }

  public void setDialogue(List<Dialogue> dialogue) {
    Dialogue = dialogue;
  }

  public List<Dialogue> getDialogueAfter() {
    return DialogueAfter;
  }

  public void setDialogueAfter(List<Dialogue> dialogueAfter) {
    DialogueAfter = dialogueAfter;
  }

  public List<Level> getLevels() {
    return Levels;
  }

  public void setLevels(List<Level> levels) {
    Levels = levels;
  }
}
