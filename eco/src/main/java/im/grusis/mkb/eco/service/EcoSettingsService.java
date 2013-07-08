package im.grusis.mkb.eco.service;

import java.util.List;
import javax.annotation.PostConstruct;

import im.grusis.mkb.eco.model.*;
import im.grusis.mkb.eco.repository.EcoSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EcoSettingsService {

  public static final String BOSS = "boss";
  public static final String MAZE = "maze";
  public static final String MAP = "map";
  public static final String FENERGY = "fenergy";
  public static final String LEGION = "legion";
  public static final String FRIENDS = "friends";


  @Autowired EcoSettingsRepository ecoSettingsRepository;

  private BossPoolSettings bossPool;
  private MazePoolSettings mazePool;
  private MapPoolSettings mapPool;
  private FEnergyPoolSettings fenergyPool;
  private LegionPoolSettings legionPool;
  private FriendsPoolSettings friendsPool;

  @PostConstruct
  public void init() {
    bossPool = ecoSettingsRepository.read(BOSS, BossPoolSettings.class);
    mazePool = ecoSettingsRepository.read(MAZE, MazePoolSettings.class);
    mapPool = ecoSettingsRepository.read(MAP, MapPoolSettings.class);
    fenergyPool = ecoSettingsRepository.read(FENERGY, FEnergyPoolSettings.class);
    legionPool = ecoSettingsRepository.read(LEGION, LegionPoolSettings.class);
    friendsPool = ecoSettingsRepository.read(FRIENDS, FriendsPoolSettings.class);
  }

  public void addBossPoolUser(List<String> users) {
    if(bossPool == null) {
      bossPool = new BossPoolSettings(BOSS);
    }
    bossPool.addUsers(users);
    ecoSettingsRepository.createOrUpdateSettings(bossPool);
  }

  public void removeBossPoolUser(List<String> users) {
    if(bossPool != null) {
      bossPool.removeUsers(users);
      ecoSettingsRepository.createOrUpdateSettings(bossPool);
    }
  }

  public void addMazePoolUser(List<String> users) {
    if(mazePool == null) {
      mazePool = new MazePoolSettings(MAZE);
    }
    mazePool.addUsers(users);
    ecoSettingsRepository.createOrUpdateSettings(mazePool);
  }

  public void removeMazePoolUser(List<String> users) {
    if(mazePool != null) {
      mazePool.removeUsers(users);
      ecoSettingsRepository.createOrUpdateSettings(mazePool);
    }
  }

  public void addMapPoolUser(List<String> users) {
    if(mapPool == null) {
      mapPool = new MapPoolSettings(MAP);
    }
    mapPool.addUsers(users);
    ecoSettingsRepository.createOrUpdateSettings(mapPool);
  }

  public void removeMapPoolUser(List<String> users) {
    if(mapPool != null) {
      mapPool.removeUsers(users);
      ecoSettingsRepository.createOrUpdateSettings(mapPool);
    }
  }

  public void addFenergyPoolUser(List<String> users) {
    if(fenergyPool == null) {
      fenergyPool = new FEnergyPoolSettings(FENERGY);
    }
    fenergyPool.addUsers(users);
    ecoSettingsRepository.createOrUpdateSettings(fenergyPool);
  }

  public void removeFenergyPoolUser(List<String> users) {
    if(fenergyPool != null) {
      fenergyPool.removeUsers(users);
      ecoSettingsRepository.createOrUpdateSettings(fenergyPool);
    }
  }

  public void addLegionPoolUser(List<String> users) {
    if(legionPool == null) {
      legionPool = new LegionPoolSettings(LEGION);
    }
    legionPool.addUsers(users);
    ecoSettingsRepository.createOrUpdateSettings(legionPool);
  }

  public void removeLegionPoolUser(List<String> users) {
    if(legionPool != null) {
      legionPool.removeUsers(users);
      ecoSettingsRepository.createOrUpdateSettings(legionPool);
    }
  }

  public void addFriendsPoolUser(List<String> users) {
    if(friendsPool == null) {
      friendsPool = new FriendsPoolSettings(FRIENDS);
    }
    friendsPool.addUsers(users);
    ecoSettingsRepository.createOrUpdateSettings(friendsPool);
  }

  public void removeFriendsPoolUser(List<String> users) {
    if(friendsPool != null) {
      friendsPool.removeUsers(users);
      ecoSettingsRepository.createOrUpdateSettings(friendsPool);
    }
  }

  public BossPoolSettings getBossPool() {
    return bossPool;
  }

  public MazePoolSettings getMazePool() {
    return mazePool;
  }

  public MapPoolSettings getMapPool() {
    return mapPool;
  }

  public FEnergyPoolSettings getFenergyPool() {
    return fenergyPool;
  }

  public LegionPoolSettings getLegionPool() {
    return legionPool;
  }

  public FriendsPoolSettings getFriendsPool() {
    return friendsPool;
  }
}
