package im.grusis.mkb.eco.service;

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
    bossPool = ecoSettingsRepository.read(MAZE, UserPoolSettings.class);
    bossPool = ecoSettingsRepository.read(MAP, UserPoolSettings.class);
    bossPool = ecoSettingsRepository.read(FENERGY, UserPoolSettings.class);
    bossPool = ecoSettingsRepository.read(LEGION, UserPoolSettings.class);
    bossPool = ecoSettingsRepository.read(FRIENDS, UserPoolSettings.class);
  }

}
