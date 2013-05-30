package im.grusis.mkb.emulator.core.model.basic;

import java.util.Map;

/**
 * User: Mothership
 * Date: 13-5-30
 * Time: 上午12:00
 */
public class Goods {
  private int GoodsId;
  private String StartDate;
  private String EndDate;
  private String StartTime;
  private String EndTime;
  private String Name;
  private String Content;
  private String ExtraContent;
  private int Coins;
  private int Cash;
  private int Ticket;
  private int Add;
  private int Max;
  private int Num;
  private Map<Integer,Map<Integer, Double>> Color;
  private int Count;

  public int getGoodsId() {
    return GoodsId;
  }

  public void setGoodsId(int goodsId) {
    GoodsId = goodsId;
  }

  public String getStartDate() {
    return StartDate;
  }

  public void setStartDate(String startDate) {
    StartDate = startDate;
  }

  public String getEndDate() {
    return EndDate;
  }

  public void setEndDate(String endDate) {
    EndDate = endDate;
  }

  public String getStartTime() {
    return StartTime;
  }

  public void setStartTime(String startTime) {
    StartTime = startTime;
  }

  public String getEndTime() {
    return EndTime;
  }

  public void setEndTime(String endTime) {
    EndTime = endTime;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getContent() {
    return Content;
  }

  public void setContent(String content) {
    Content = content;
  }

  public String getExtraContent() {
    return ExtraContent;
  }

  public void setExtraContent(String extraContent) {
    ExtraContent = extraContent;
  }

  public int getCoins() {
    return Coins;
  }

  public void setCoins(int coins) {
    Coins = coins;
  }

  public int getCash() {
    return Cash;
  }

  public void setCash(int cash) {
    Cash = cash;
  }

  public int getTicket() {
    return Ticket;
  }

  public void setTicket(int ticket) {
    Ticket = ticket;
  }

  public int getAdd() {
    return Add;
  }

  public void setAdd(int add) {
    Add = add;
  }

  public int getMax() {
    return Max;
  }

  public void setMax(int max) {
    Max = max;
  }

  public int getNum() {
    return Num;
  }

  public void setNum(int num) {
    Num = num;
  }

  public Map<Integer, Map<Integer, Double>> getColor() {
    return Color;
  }

  public void setColor(Map<Integer, Map<Integer, Double>> color) {
    Color = color;
  }

  public int getCount() {
    return Count;
  }

  public void setCount(int count) {
    Count = count;
  }
}
