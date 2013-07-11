package im.grusis.mkb.eco.model;

public class CardRating {
  private int overall;
  private int againstCard;
  private int againstHero;
  private int againstTheif;
  private int againstBoss;
  private int protectHero;
  private int protectAllies;
  private int maxStreng;
  private int maxUse;
  private int maxKeep;

  public CardRating() {
  }

  public CardRating(int overall, int againstCard, int againstHero, int againstTheif, int againstBoss, int protectHero, int protectAllies, int maxStreng, int maxUse, int maxKeep) {
    this.overall = overall;
    this.againstCard = againstCard;
    this.againstHero = againstHero;
    this.againstTheif = againstTheif;
    this.againstBoss = againstBoss;
    this.protectHero = protectHero;
    this.protectAllies = protectAllies;
    this.maxStreng = maxStreng;
    this.maxUse = maxUse;
    this.maxKeep = maxKeep;
  }

  public static CardRating GetDefaultCardRating(int star) {
    CardRating ret = new CardRating();
    int defaultScore = star * 100;
    int maxStreng;
    int maxUse;
    int maxKeep;
    switch(star) {
      case 1:
      case 2:
        maxStreng = 0;
        maxUse = 0;
        maxKeep = 0;
        break;
      case 3:
        maxStreng = 0;
        maxUse = 0;
        maxKeep = Integer.MAX_VALUE;
        break;
      case 4:
        maxStreng = 2;
        maxUse = 2;
        maxKeep = Integer.MAX_VALUE;
        break;
      default:
        maxStreng = 5;
        maxUse = 5;
        maxKeep = Integer.MAX_VALUE;
    }
    return new CardRating(defaultScore, defaultScore, defaultScore, defaultScore, defaultScore, 0, 0, maxStreng, maxUse, maxKeep);
  }

  public int getOverall() {
    return overall;
  }

  public int getAgainstCard() {
    return againstCard;
  }

  public int getAgainstHero() {
    return againstHero;
  }

  public int getAgainstTheif() {
    return againstTheif;
  }

  public int getAgainstBoss() {
    return againstBoss;
  }

  public int getProtectHero() {
    return protectHero;
  }

  public int getProtectAllies() {
    return protectAllies;
  }

  public int getMaxStreng() {
    return maxStreng;
  }

  public int getMaxUse() {
    return maxUse;
  }

  public int getMaxKeep() {
    return maxKeep;
  }
}
