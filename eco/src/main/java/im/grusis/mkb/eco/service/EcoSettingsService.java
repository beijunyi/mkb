package im.grusis.mkb.eco.service;

import java.util.List;
import javax.annotation.PostConstruct;

import im.grusis.mkb.eco.model.UserPoolSettings;
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

  private UserPoolSettings bossPool;
  private UserPoolSettings mazePool;
  private UserPoolSettings mapPool;
  private UserPoolSettings fenergyPool;
  private UserPoolSettings legionPool;
  private UserPoolSettings friendsPool;

  @PostConstruct
  public void init() {
    bossPool = ecoSettingsRepository.read(BOSS, UserPoolSettings.class);
    mazePool = ecoSettingsRepository.read(MAZE, UserPoolSettings.class);
    mapPool = ecoSettingsRepository.read(MAP, UserPoolSettings.class);
    fenergyPool = ecoSettingsRepository.read(FENERGY, UserPoolSettings.class);
    legionPool = ecoSettingsRepository.read(LEGION, UserPoolSettings.class);
    friendsPool = ecoSettingsRepository.read(FRIENDS, UserPoolSettings.class);
  }

  public void addBossPoolUser(List<String> users) {
    if(bossPool == null) {
      bossPool = new UserPoolSettings(BOSS);
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
      mazePool = new UserPoolSettings(MAZE);
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
      mapPool = new UserPoolSettings(MAP);
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
      fenergyPool = new UserPoolSettings(FENERGY);
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
      legionPool = new UserPoolSettings(LEGION);
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

  public void addFriendPoolUser(List<String> users) {
    if(friendsPool == null) {
      friendsPool = new UserPoolSettings(FRIENDS);
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


}
