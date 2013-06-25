package im.grusis.mkb.core.service;

import java.util.*;
import javax.annotation.PostConstruct;

import im.grusis.mkb.core.emulator.game.model.basic.*;
import im.grusis.mkb.core.emulator.web.model.basic.GameServer;
import im.grusis.mkb.core.repository.AssetsRepository;
import im.grusis.mkb.core.repository.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Mothership
 * Date: 13-6-5
 * Time: 下午8:29
 */
@Service
public class AssetsService {

  private static final Logger LOG = LoggerFactory.getLogger(AssetsService.class);

  @Autowired private AssetsRepository assetsRepository;

  private AllCard cards;
  private Runes runes;
  private AllSkill skills;
  private MapStageAll stages;
  private GoodsList goodsList;
  private List<GameServer> gameServers;

  private Map<Integer, CardDef> cardLookup = new LinkedHashMap<Integer, CardDef>();
  private Map<Integer, RuneDef> runeLookup = new LinkedHashMap<Integer, RuneDef>();
  private Map<Integer, SkillDef> skillLookup = new LinkedHashMap<Integer, SkillDef>();
  private Map<Integer, MapDef> mapStageLookup = new LinkedHashMap<Integer, MapDef>();
  private Map<Integer, MapStageDef> mapStageDetailLookup = new LinkedHashMap<Integer, MapStageDef>();
  private Map<Integer, Integer> mazeDependency = new TreeMap<Integer, Integer>();
  private Map<Integer, Goods> goodsLookup = new LinkedHashMap<Integer, Goods>();
  private Map<String, GameServer> gameServerLookup = new LinkedHashMap<String, GameServer>();

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

  public Map<Integer, CardDef> updateCardLookup(CardAssets cardAssets) {
    if(cardAssets == null) {
      return null;
    }
    cardLookup.clear();
    AllCard cards = cardAssets.getAsset();
    List<CardDef> cardList = cards.getCards();
    for(CardDef card : cardList) {
      cardLookup.put(card.getCardId(), card);
    }
    return cardLookup;
  }

  public Map<Integer, RuneDef> updateRuneLookup(RuneAssets runeAssets) {
    if(runeAssets == null) {
      return null;
    }
    runeLookup.clear();
    Runes runes = runeAssets.getAsset();
    List<RuneDef> runeList = runes.getRunes();
    for(RuneDef rune : runeList) {
      runeLookup.put(rune.getRuneId(), rune);
    }
    return runeLookup;
  }

  public Map<Integer, SkillDef> updateSkillLookup(SkillAssets skillAssets) {
    if(skillAssets == null) {
      return null;
    }
    skillLookup.clear();
    AllSkill skills = skillAssets.getAsset();
    List<SkillDef> skillList = skills.getSkills();
    for(SkillDef skill : skillList) {
      skillLookup.put(skill.getSkillId(), skill);
    }
    return skillLookup;
  }

  public Map<Integer, MapDef> updateMapStageLookup(MapStageAssets mapStageAssets) {
    if(mapStageAssets == null) {
      return null;
    }
    mapStageLookup.clear();
    mapStageDetailLookup.clear();
    mazeDependency.clear();
    MapStageAll maps = mapStageAssets.getAsset();
    for(MapDef map : maps) {
      mapStageLookup.put(map.getMapStageId(), map);
      boolean hasMaze = false;
      int boss = -1;
      for(MapStageDef detail : map.getMapStageDetails()) {
        mapStageDetailLookup.put(detail.getMapStageDetailId(), detail);
        int type = detail.getType();
        if(type == MapStageDef.MazeLevel) {
          hasMaze = true;
        } else if(type == MapStageDef.BossLevel) {
          boss = detail.getMapStageDetailId();
        }
      }
      if(hasMaze) {
        if(boss == -1) {
          LOG.warn("Cannot find boss level for map {} {}", map.getMapStageId(), map.getName());
        } else {
          mazeDependency.put(map.getMapStageId(), boss);
        }
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
      gameServerLookup.put(gameServer.getGsDesc(), gameServer);
    }
    return gameServerLookup;
  }

  public CardDef findCard(int id) {
    return cardLookup.get(id);
  }

  public RuneDef findRune(int id) {
    return runeLookup.get(id);
  }

  public SkillDef findSkill(int id) {
    return skillLookup.get(id);
  }

  public MapDef findMapStage(int id) {
    return mapStageLookup.get(id);
  }

  public MapStageDef findMapStageDetail(int id) {
    return mapStageDetailLookup.get(id);
  }

  public Goods findGoods(int id) {
    return goodsLookup.get(id);
  }

  public GameServer findGameServer(String desc) {
    return gameServerLookup.get(desc);
  }

  public Map<Integer, CardDef> saveAssets(AllCard cards) {
    this.cards = cards;
    CardAssets cardAssets = new CardAssets();
    cardAssets.setAsset(cards);
    assetsRepository.createOrUpdateAssets(cardAssets);
    return updateCardLookup(cardAssets);
  }

  public Map<Integer, RuneDef> saveAssets(Runes runes) {
    this.runes = runes;
    RuneAssets runeAssets = new RuneAssets();
    runeAssets.setAsset(runes);
    assetsRepository.createOrUpdateAssets(runeAssets);
    return updateRuneLookup(runeAssets);
  }

  public Map<Integer, SkillDef> saveAssets(AllSkill skills) {
    this.skills = skills;
    SkillAssets skillAssets = new SkillAssets();
    skillAssets.setAsset(skills);
    assetsRepository.createOrUpdateAssets(skillAssets);
    return updateSkillLookup(skillAssets);
  }

  public Map<Integer, MapDef> saveAssets(MapStageAll stages) {
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

  public Map<Integer, CardDef> getCardLookup() {
    return cardLookup;
  }

  public Map<Integer, RuneDef> getRuneLookup() {
    return runeLookup;
  }

  public Map<Integer, SkillDef> getSkillLookup() {
    return skillLookup;
  }

  public Map<Integer, MapDef> getMapStageLookup() {
    return mapStageLookup;
  }

  public Map<Integer, MapStageDef> getMapStageDetailLookup() {
    return mapStageDetailLookup;
  }

  public Map<Integer, Integer> getMazeDependency() {
    return mazeDependency;
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
