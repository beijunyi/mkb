package im.grusis.mkb.eco.service;

import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

import im.grusis.mkb.core.emulator.game.model.basic.CardDef;
import im.grusis.mkb.core.service.AssetsService;
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

  public static final String CARD_RATINGS = "card_ratings";


  @Autowired EcoSettingsRepository ecoSettingsRepository;
  @Autowired AssetsService assetsService;

  private BossPoolSettings bossPool;
  private MazePoolSettings mazePool;
  private MapPoolSettings mapPool;
  private FenergyPoolSettings fenergyPool;
  private LegionPoolSettings legionPool;
  private FriendsPoolSettings friendsPool;
  private CardRatingSettings cardRatingSettings;

  @PostConstruct
  public void init() {
    bossPool = ecoSettingsRepository.read(BOSS, BossPoolSettings.class);
    mazePool = ecoSettingsRepository.read(MAZE, MazePoolSettings.class);
    mapPool = ecoSettingsRepository.read(MAP, MapPoolSettings.class);
    fenergyPool = ecoSettingsRepository.read(FENERGY, FenergyPoolSettings.class);
    legionPool = ecoSettingsRepository.read(LEGION, LegionPoolSettings.class);
    friendsPool = ecoSettingsRepository.read(FRIENDS, FriendsPoolSettings.class);
    cardRatingSettings = ecoSettingsRepository.read(CARD_RATINGS, CardRatingSettings.class);
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
      fenergyPool = new FenergyPoolSettings(FENERGY);
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

  public BossPoolSettings updateBossPool(String username, BossSettings bossSettings) {
    if(bossPool == null) {
      bossPool = new BossPoolSettings(BOSS);
    }
    if(username == null) {
      bossPool.setDefaultSettings(bossSettings);
    } else {
      bossPool.setSetting(username, bossSettings);
    }
    return bossPool;
  }

  public MazePoolSettings getMazePool() {
    return mazePool;
  }

  public MazePoolSettings updateMazePool(String username, MazeSettings mazeSettings) {
    if(mazePool == null) {
      mazePool = new MazePoolSettings(MAZE);
    }
    if(username == null) {
      mazePool.setDefaultSettings(mazeSettings);
    } else {
      mazePool.setSetting(username, mazeSettings);
    }
    return mazePool;
  }

  public MapPoolSettings getMapPool() {
    return mapPool;
  }

  public MapPoolSettings updateMapPool(String username, MapSettings mapSettings) {
    if(mapPool == null) {
      mapPool = new MapPoolSettings(MAP);
    }
    if(username == null) {
      mapPool.setDefaultSettings(mapSettings);
    } else {
      mapPool.setSetting(username, mapSettings);
    }
    return mapPool;
  }

  public FenergyPoolSettings getFenergyPool() {
    return fenergyPool;
  }

  public FenergyPoolSettings updateMapPool(String username, FenergySettings fenergySettings) {
    if(fenergyPool == null) {
      fenergyPool = new FenergyPoolSettings(FENERGY);
    }
    if(username == null) {
      fenergyPool.setDefaultSettings(fenergySettings);
    } else {
      fenergyPool.setSetting(username, fenergySettings);
    }
    return fenergyPool;
  }

  public LegionPoolSettings getLegionPool() {
    return legionPool;
  }

  public LegionPoolSettings updateLegionPool(String username, LegionSettings legionSettings) {
    if(legionPool == null) {
      legionPool = new LegionPoolSettings(LEGION);
    }
    if(username == null) {
      legionPool.setDefaultSettings(legionSettings);
    } else {
      legionPool.setSetting(username, legionSettings);
    }
    return legionPool;
  }

  public FriendsPoolSettings getFriendsPool() {
    return friendsPool;
  }

  public FriendsPoolSettings updateFriendsPool(String username, FriendsSettings friendsSettings) {
    if(friendsPool == null) {
      friendsPool = new FriendsPoolSettings(FRIENDS);
    }
    if(username == null) {
      friendsPool.setDefaultSettings(friendsSettings);
    } else {
      friendsPool.setSetting(username, friendsSettings);
    }
    return friendsPool;
  }

  public CardRatingSettings getCardRatingSettings() {
    if(cardRatingSettings == null) {
      cardRatingSettings = new CardRatingSettings(CARD_RATINGS);
    }
    Map<Integer, CardRating> cardRatingMap = cardRatingSettings.getCardRatings();
    Map<Integer, CardDef> cards = assetsService.getCardLookup();
    boolean save = false;
    for(Map.Entry<Integer, CardDef> cardDefEntry : cards.entrySet()) {
      int cardId = cardDefEntry.getKey();
      CardRating cardRating = cardRatingMap.get(cardId);
      if(cardRating == null) {
        int star = cardDefEntry.getValue().getColor();
        cardRating = CardRating.GetDefaultCardRating(star);
        cardRatingMap.put(cardId, cardRating);
        save = true;
      }
    }
    if(save) {
      ecoSettingsRepository.createOrUpdateSettings(cardRatingSettings);
    }
    return cardRatingSettings;
  }

  public CardRating modifyCardRatting(int cardId, CardRating cardRating) {
    if(cardRatingSettings == null) {
      cardRatingSettings = new CardRatingSettings(CARD_RATINGS);
    }
    Map<Integer, CardRating> cardRatingMap = cardRatingSettings.getCardRatings();
    cardRatingMap.put(cardId, cardRating);
    return cardRating;
  }
}
