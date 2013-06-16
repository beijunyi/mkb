package im.grusis.mkb.service;

import java.util.*;
import javax.annotation.PostConstruct;

import im.grusis.mkb.emulator.emulator.core.model.basic.*;
import im.grusis.mkb.emulator.emulator.passport.model.basic.GameServer;
import im.grusis.mkb.internal.*;
import im.grusis.mkb.repository.AssetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午8:29
 */
@Service
public class AssetsService {

  @Autowired private AssetsRepository assetsRepository;

  private AllCard cards;
  private Runes runes;
  private AllSkill skills;
  private MapStageAll stages;
  private GoodsList goodsList;
  private List<GameServer> gameServers;

  private Map<Integer, Card> cardLookup = new LinkedHashMap<Integer, Card>();
  private Map<Integer, Rune> runeLookup = new LinkedHashMap<Integer, Rune>();
  private Map<Integer, Skill> skillLookup = new LinkedHashMap<Integer, Skill>();
  private Map<Integer, MapStage> mapStageLookup = new LinkedHashMap<Integer, MapStage>();
  private Map<Integer, MapStageDetail> mapStageDetailLookup = new LinkedHashMap<Integer, MapStageDetail>();
  private Map<Integer, Goods> goodsLookup = new LinkedHashMap<Integer, Goods>();
  private Map<String, GameServer> gameServerLookup = new LinkedHashMap<String, GameServer>();
  private Map<String, GameServer> gameServerDescCache = new LinkedHashMap<String, GameServer>();

  @PostConstruct
  public void prepareLookups() {
    CardAssets cardAssets = assetsRepository.getAssets(CardAssets.AssetName, CardAssets.class);
    updateCardLookup(cardAssets);
    RuneAssets runeAssets = assetsRepository.getAssets(RuneAssets.AssetName, RuneAssets.class);
    updateRuneLookup(runeAssets);
    SkillAssets skillAssets = assetsRepository.getAssets(SkillAssets.AssetName, SkillAssets.class);
    updateSkillLookup(skillAssets);
    MapStageAssets mapStageAssets = assetsRepository.getAssets(MapStageAssets.AssetName, MapStageAssets.class);
    updateMapStageLookup(mapStageAssets);
    GoodsAssets goodsAssets = assetsRepository.getAssets(GoodsAssets.AssetName, GoodsAssets.class);
    updateGoodsLookup(goodsAssets);
    GameServerAssets gameServerAssets = assetsRepository.getAssets(GameServerAssets.AssetName, GameServerAssets.class);
    updateGameServerLookup(gameServerAssets);
  }

  public Map<Integer, Card> updateCardLookup(CardAssets cardAssets) {
    if(cardAssets == null) {
      return null;
    }
    cardLookup.clear();
    AllCard cards = cardAssets.getAsset();
    List<Card> cardList = cards.getCards();
    for(Card card : cardList) {
      cardLookup.put(card.getCardId(), card);
    }
    return cardLookup;
  }

  public Map<Integer, Rune> updateRuneLookup(RuneAssets runeAssets) {
    if(runeAssets == null) {
      return null;
    }
    runeLookup.clear();
    Runes runes = runeAssets.getAsset();
    List<Rune> runeList = runes.getRunes();
    for(Rune rune : runeList) {
      runeLookup.put(rune.getRuneId(), rune);
    }
    return runeLookup;
  }

  public Map<Integer, Skill> updateSkillLookup(SkillAssets skillAssets) {
    if(skillAssets == null) {
      return null;
    }
    skillLookup.clear();
    AllSkill skills = skillAssets.getAsset();
    List<Skill> skillList = skills.getSkills();
    for(Skill skill : skillList) {
      skillLookup.put(skill.getSkillId(), skill);
    }
    return skillLookup;
  }

  public Map<Integer, MapStage> updateMapStageLookup(MapStageAssets mapStageAssets) {
    if(mapStageAssets == null) {
      return null;
    }
    mapStageLookup.clear();
    mapStageDetailLookup.clear();
    MapStageAll mapStages = mapStageAssets.getAsset();
    for(MapStage stage : mapStages) {
      mapStageLookup.put(stage.getMapStageId(), stage);
      for(MapStageDetail detail : stage.getMapStageDetails()) {
        mapStageDetailLookup.put(detail.getMapStageDetailId(), detail);
      }
    }
    return mapStageLookup;
  }

  public Map<Integer, Goods> updateGoodsLookup(GoodsAssets goodsAssets) {
    if(goodsAssets == null) {
      return null;
    }
    goodsLookup.clear();
    GoodsList goodsList = goodsAssets.getAsset();
    for(Goods goods : goodsList) {
      goodsLookup.put(goods.getGoodsId(), goods);
    }
    return goodsLookup;
  }

  public Map<String, GameServer> updateGameServerLookup(GameServerAssets gameServerAssets) {
    if(gameServerAssets == null) {
      return null;
    }
    gameServerLookup.clear();
    List<GameServer> gameServers = gameServerAssets.getAsset();
    for(GameServer gameServer : gameServers) {
      gameServerLookup.put(gameServer.getGsName(), gameServer);
    }
    return gameServerLookup;
  }

  public Card findCard(int id) {
    return cardLookup.get(id);
  }

  public Rune findRune(int id) {
    return runeLookup.get(id);
  }

  public Skill findSkill(int id) {
    return skillLookup.get(id);
  }

  public MapStage findMapStage(int id) {
    return mapStageLookup.get(id);
  }

  public MapStageDetail findMapStageDetail(int id) {
    return mapStageDetailLookup.get(id);
  }

  public Goods findGoods(int id) {
    return goodsLookup.get(id);
  }

  public GameServer findGameServerByName(String name) {
    return gameServerLookup.get(name);
  }

  public GameServer findGameServerByDescription(String desc) {
    GameServer ret = gameServerDescCache.get(desc);
    Collection<GameServer> gameServers = gameServerLookup.values();
    for(GameServer gameServer : gameServers) {
      if(gameServer.getGsDesc().contains(desc)) {
        gameServerDescCache.put(desc, gameServer);
        ret = gameServer;
        break;
      }
    }
    return ret;
  }

  public Map<Integer, Card> saveAssets(AllCard cards) {
    this.cards = cards;
    CardAssets cardAssets = new CardAssets();
    cardAssets.setAsset(cards);
    assetsRepository.createOrUpdateAssets(cardAssets);
    return updateCardLookup(cardAssets);
  }

  public Map<Integer, Rune> saveAssets(Runes runes) {
    this.runes = runes;
    RuneAssets runeAssets = new RuneAssets();
    runeAssets.setAsset(runes);
    assetsRepository.createOrUpdateAssets(runeAssets);
    return updateRuneLookup(runeAssets);
  }

  public Map<Integer, Skill> saveAssets(AllSkill skills) {
    this.skills = skills;
    SkillAssets skillAssets = new SkillAssets();
    skillAssets.setAsset(skills);
    assetsRepository.createOrUpdateAssets(skillAssets);
    return updateSkillLookup(skillAssets);
  }

  public Map<Integer, MapStage> saveAssets(MapStageAll stages) {
    this.stages = stages;
    MapStageAssets mapStageAssets = new MapStageAssets();
    mapStageAssets.setAsset(stages);
    assetsRepository.createOrUpdateAssets(mapStageAssets);
    return updateMapStageLookup(mapStageAssets);
  }

  public Map<Integer, Goods> saveAssets(GoodsList goodsList) {
    this.goodsList = goodsList;
    GoodsAssets goodsAssets = new GoodsAssets();
    goodsAssets.setAsset(goodsList);
    assetsRepository.createOrUpdateAssets(goodsAssets);
    return updateGoodsLookup(goodsAssets);
  }

  public Map<String, GameServer> saveAssets(List<GameServer> gameServers) {
    this.gameServers = gameServers;
    GameServerAssets gameServerAssets = new GameServerAssets();
    gameServerAssets.setAsset(gameServers);
    assetsRepository.createOrUpdateAssets(gameServerAssets);
    return updateGameServerLookup(gameServerAssets);
  }

  public Map<Integer, Card> getCardLookup() {
    return cardLookup;
  }

  public Map<Integer, Rune> getRuneLookup() {
    return runeLookup;
  }

  public Map<Integer, Skill> getSkillLookup() {
    return skillLookup;
  }

  public Map<Integer, MapStage> getMapStageLookup() {
    return mapStageLookup;
  }

  public Map<Integer, MapStageDetail> getMapStageDetailLookup() {
    return mapStageDetailLookup;
  }

  public Map<Integer, Goods> getGoodsLookup() {
    return goodsLookup;
  }

  public Map<String, GameServer> getGameServerLookup() {
    return gameServerLookup;
  }

  public AllCard getCards() {
    return cards;
  }

  public Runes getRunes() {
    return runes;
  }

  public AllSkill getSkills() {
    return skills;
  }

  public MapStageAll getStages() {
    return stages;
  }

  public GoodsList getGoods() {
    return goodsList;
  }

  public List<GameServer> getGameServers() {
    return gameServers;
  }
}
