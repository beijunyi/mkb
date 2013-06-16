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

  public void updateCardLookup(CardAssets cardAssets) {
    if(cardAssets == null) {
      return;
    }
    cardLookup.clear();
    AllCard cards = cardAssets.getAsset();
    List<Card> cardList = cards.getCards();
    for(Card card : cardList) {
      cardLookup.put(card.getCardId(), card);
    }
  }

  public void updateRuneLookup(RuneAssets runeAssets) {
    if(runeAssets == null) {
      return;
    }
    runeLookup.clear();
    Runes runes = runeAssets.getAsset();
    List<Rune> runeList = runes.getRunes();
    for(Rune rune : runeList) {
      runeLookup.put(rune.getRuneId(), rune);
    }
  }

  public void updateSkillLookup(SkillAssets skillAssets) {
    if(skillAssets == null) {
      return;
    }
    skillLookup.clear();
    AllSkill skills = skillAssets.getAsset();
    List<Skill> skillList = skills.getSkills();
    for(Skill skill : skillList) {
      skillLookup.put(skill.getSkillId(), skill);
    }
  }

  public void updateMapStageLookup(MapStageAssets mapStageAssets) {
    if(mapStageAssets == null) {
      return;
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
  }

  public void updateGoodsLookup(GoodsAssets goodsAssets) {
    if(goodsAssets == null) {
      return;
    }
    goodsLookup.clear();
    GoodsList goodsList = goodsAssets.getAsset();
    for(Goods goods : goodsList) {
      goodsLookup.put(goods.getGoodsId(), goods);
    }
  }

  public void updateGameServerLookup(GameServerAssets gameServerAssets) {
    if(gameServerAssets == null) {
      return;
    }
    gameServerLookup.clear();
    List<GameServer> gameServers = gameServerAssets.getAsset();
    for(GameServer gameServer : gameServers) {
      gameServerLookup.put(gameServer.getGsName(), gameServer);
    }
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

  public void saveAssets(AllCard cards) {
    this.cards = cards;
    CardAssets cardAssets = new CardAssets();
    cardAssets.setAsset(cards);
    assetsRepository.createOrUpdateAssets(cardAssets);
    updateCardLookup(cardAssets);
  }

  public void saveAssets(Runes runes) {
    this.runes = runes;
    RuneAssets runeAssets = new RuneAssets();
    runeAssets.setAsset(runes);
    assetsRepository.createOrUpdateAssets(runeAssets);
    updateRuneLookup(runeAssets);
  }

  public void saveAssets(AllSkill skills) {
    this.skills = skills;
    SkillAssets skillAssets = new SkillAssets();
    skillAssets.setAsset(skills);
    assetsRepository.createOrUpdateAssets(skillAssets);
    updateSkillLookup(skillAssets);
  }

  public void saveAssets(MapStageAll stages) {
    this.stages = stages;
    MapStageAssets mapStageAssets = new MapStageAssets();
    mapStageAssets.setAsset(stages);
    assetsRepository.createOrUpdateAssets(mapStageAssets);
    updateMapStageLookup(mapStageAssets);
  }

  public void saveAssets(GoodsList goodsList) {
    this.goodsList = goodsList;
    GoodsAssets goodsAssets = new GoodsAssets();
    goodsAssets.setAsset(goodsList);
    assetsRepository.createOrUpdateAssets(goodsAssets);
    updateGoodsLookup(goodsAssets);
  }

  public void saveAssets(List<GameServer> gameServers) {
    this.gameServers = gameServers;
    GameServerAssets gameServerAssets = new GameServerAssets();
    gameServerAssets.setAsset(gameServers);
    assetsRepository.createOrUpdateAssets(gameServerAssets);
    updateGameServerLookup(gameServerAssets);
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
