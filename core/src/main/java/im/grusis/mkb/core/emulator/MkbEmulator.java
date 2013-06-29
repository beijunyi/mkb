package im.grusis.mkb.core.emulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Mothership
 * Date: 13-6-29
 * Time: 下午4:53
 */
@Component
public class MkbEmulator {
  @Autowired EmulatorArena arena;
  @Autowired EmulatorBoss boss;
  @Autowired EmulatorCard card;
  @Autowired EmulatorChip chip;
  @Autowired EmulatorCore core;
  @Autowired EmulatorFEnergy fEnergy;
  @Autowired EmulatorFriend friend;
  @Autowired EmulatorLegion legion;
  @Autowired EmulatorMapStage mapStage;
  @Autowired EmulatorMaze maze;
  @Autowired EmulatorMelee melee;
  @Autowired EmulatorRune rune;
  @Autowired EmulatorShop shop;
  @Autowired EmulatorStreng streng;
  @Autowired EmulatorUser user;
  @Autowired EmulatorWeb web;

  public EmulatorArena arena() {
    return arena;
  }

  public EmulatorBoss boss() {
    return boss;
  }

  public EmulatorCard card() {
    return card;
  }

  public EmulatorChip chip() {
    return chip;
  }

  public EmulatorCore core() {
    return core;
  }

  public EmulatorFEnergy fEnergy() {
    return fEnergy;
  }

  public EmulatorFriend friend() {
    return friend;
  }

  public EmulatorLegion legion() {
    return legion;
  }

  public EmulatorMapStage mapStage() {
    return mapStage;
  }

  public EmulatorMaze maze() {
    return maze;
  }

  public EmulatorMelee melee() {
    return melee;
  }

  public EmulatorRune rune() {
    return rune;
  }

  public EmulatorShop shop() {
    return shop;
  }

  public EmulatorStreng streng() {
    return streng;
  }

  public EmulatorUser user() {
    return user;
  }

  public EmulatorWeb web() {
    return web;
  }
}
