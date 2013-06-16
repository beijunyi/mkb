package im.grusis.mkb;

import im.grusis.mkb.config.RuntimeConfig;
import im.grusis.mkb.emulator.bot.BotManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 下午8:22
 */
public class TEMP2 {

//  @Test
  public void t() {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(RuntimeConfig.class);
    ctx.start();

    BotManager bm = ctx.getBean(BotManager.class);
    bm.angelBot("mkbzealot", "狂徒", 1, 331, "45ekat");

    System.currentTimeMillis();
  }

  public static void main(String args[]) {
//    new TEMP2().t();
//    String a = "{\"status\":1,\"data\":{\"BattleId\":\"1a38d44dcf5aea06a7712ec94c6375ac51b6418fe11c6\",\"Win\":1,\"ExtData\":{\"Award\":{\"Coins\":3670,\"Exp\":2490,\"CardId\":33},\"Clear\":{\"IsClear\":0,\"CardId\":0,\"Coins\":0},\"User\":{\"Level\":\"54\",\"Exp\":9264360,\"PrevExp\":7971700,\"NextExp\":9310500}},\"prepare\":null,\"AttackPlayer\":{\"Uid\":\"504309\",\"NickName\":\"wgbfunh\",\"Avatar\":\"59\",\"Sex\":1,\"Level\":\"54\",\"HP\":\"7360\",\"Cards\":[{\"UUID\":\"atk_1\",\"CardId\":\"59\",\"UserCardId\":7338006,\"Attack\":605,\"HP\":2040,\"Wait\":\"4\",\"Level\":10,\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null},{\"UUID\":\"atk_2\",\"CardId\":\"58\",\"UserCardId\":5922094,\"Attack\":560,\"HP\":995,\"Wait\":\"4\",\"Level\":10,\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null},{\"UUID\":\"atk_3\",\"CardId\":\"186\",\"UserCardId\":7000820,\"Attack\":530,\"HP\":1100,\"Wait\":\"4\",\"Level\":10,\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null},{\"UUID\":\"atk_4\",\"CardId\":\"186\",\"UserCardId\":8263813,\"Attack\":530,\"HP\":1100,\"Wait\":\"4\",\"Level\":10,\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null},{\"UUID\":\"atk_5\",\"CardId\":\"74\",\"UserCardId\":\"2869916\",\"Attack\":400,\"HP\":885,\"Wait\":\"4\",\"Level\":\"10\",\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null},{\"UUID\":\"atk_6\",\"CardId\":\"88\",\"UserCardId\":\"4248786\",\"Attack\":495,\"HP\":1220,\"Wait\":\"6\",\"Level\":\"10\",\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null},{\"UUID\":\"atk_7\",\"CardId\":\"59\",\"UserCardId\":\"2493857\",\"Attack\":605,\"HP\":2040,\"Wait\":\"4\",\"Level\":\"10\",\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null},{\"UUID\":\"atk_8\",\"CardId\":\"151\",\"UserCardId\":7697605,\"Attack\":560,\"HP\":1250,\"Wait\":\"4\",\"Level\":10,\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null},{\"UUID\":\"atk_9\",\"CardId\":\"74\",\"UserCardId\":\"2986159\",\"Attack\":400,\"HP\":885,\"Wait\":\"4\",\"Level\":\"10\",\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null},{\"UUID\":\"atk_10\",\"CardId\":\"157\",\"UserCardId\":9649691,\"Attack\":515,\"HP\":1240,\"Wait\":\"4\",\"Level\":10,\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null}],\"Runes\":[{\"UUID\":\"atkrune_11\",\"RuneId\":\"20\",\"UserRuneId\":828020,\"Level\":3},{\"UUID\":\"atkrune_12\",\"RuneId\":\"19\",\"UserRuneId\":711892,\"Level\":4},{\"UUID\":\"atkrune_13\",\"RuneId\":\"37\",\"UserRuneId\":\"404973\",\"Level\":\"4\"},{\"UUID\":\"atkrune_14\",\"RuneId\":\"35\",\"UserRuneId\":569604,\"Level\":4}],\"RemainHP\":7140},\"DefendPlayer\":{\"Uid\":0,\"NickName\":\"宝箱偷盗者\",\"Avatar\":21,\"Sex\":1,\"Level\":38,\"HP\":\"4700\",\"Cards\":[{\"UUID\":\"def_1\",\"CardId\":23,\"UserCardId\":0,\"Attack\":287,\"HP\":1088,\"Wait\":\"6\",\"Level\":3,\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null},{\"UUID\":\"def_2\",\"CardId\":112,\"UserCardId\":1,\"Attack\":424,\"HP\":873,\"Wait\":\"4\",\"Level\":8,\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null},{\"UUID\":\"def_3\",\"CardId\":112,\"UserCardId\":2,\"Attack\":424,\"HP\":873,\"Wait\":\"4\",\"Level\":8,\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null},{\"UUID\":\"def_4\",\"CardId\":24,\"UserCardId\":3,\"Attack\":264,\"HP\":917,\"Wait\":\"4\",\"Level\":3,\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null},{\"UUID\":\"def_5\",\"CardId\":87,\"UserCardId\":4,\"Attack\":348,\"HP\":1371,\"Wait\":\"6\",\"Level\":3,\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null},{\"UUID\":\"def_6\",\"CardId\":54,\"UserCardId\":5,\"Attack\":220,\"HP\":998,\"Wait\":\"4\",\"Level\":3,\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null},{\"UUID\":\"def_7\",\"CardId\":104,\"UserCardId\":6,\"Attack\":339,\"HP\":791,\"Wait\":\"4\",\"Level\":7,\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null},{\"UUID\":\"def_8\",\"CardId\":21,\"UserCardId\":7,\"Attack\":359,\"HP\":1112,\"Wait\":\"4\",\"Level\":8,\"SkillNew\":null,\"Evolution\":null,\"WashTime\":null}],\"Runes\":[{\"UUID\":\"defrune_9\",\"RuneId\":34,\"UserRuneId\":0,\"Level\":4},{\"UUID\":\"defrune_10\",\"RuneId\":7,\"UserRuneId\":1,\"Level\":1},{\"UUID\":\"defrune_11\",\"RuneId\":35,\"UserRuneId\":2,\"Level\":2}],\"RemainHP\":0},\"Battle\":[{\"Round\":1,\"isAttack\":true,\"Opps\":[{\"UUID\":\"\",\"Opp\":1021,\"Target\":null,\"Value\":-1},{\"UUID\":\"atk_5\",\"Opp\":1001,\"Target\":null,\"Value\":0},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3}]},{\"Round\":2,\"isAttack\":false,\"Opps\":[{\"UUID\":\"\",\"Opp\":1021,\"Target\":null,\"Value\":-1},{\"UUID\":\"def_1\",\"Opp\":1001,\"Target\":null,\"Value\":0},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3}]},{\"Round\":3,\"isAttack\":true,\"Opps\":[{\"UUID\":\"\",\"Opp\":1021,\"Target\":null,\"Value\":-1},{\"UUID\":\"atk_6\",\"Opp\":1001,\"Target\":null,\"Value\":0},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3}]},{\"Round\":4,\"isAttack\":false,\"Opps\":[{\"UUID\":\"\",\"Opp\":1021,\"Target\":null,\"Value\":-1},{\"UUID\":\"def_6\",\"Opp\":1001,\"Target\":null,\"Value\":0},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3}]},{\"Round\":5,\"isAttack\":true,\"Opps\":[{\"UUID\":\"\",\"Opp\":1021,\"Target\":null,\"Value\":-1},{\"UUID\":\"atk_4\",\"Opp\":1001,\"Target\":null,\"Value\":0},{\"UUID\":\"atk_5\",\"Opp\":1002,\"Target\":null,\"Value\":0},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3},{\"UUID\":\"atk_5\",\"Opp\":1030,\"Target\":[\"def\"],\"Value\":400},{\"UUID\":\"atk_5\",\"Opp\":1022,\"Target\":[\"def\"],\"Value\":-400},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3}]},{\"Round\":6,\"isAttack\":false,\"Opps\":[{\"UUID\":\"\",\"Opp\":1021,\"Target\":null,\"Value\":-1},{\"UUID\":\"def_8\",\"Opp\":1001,\"Target\":null,\"Value\":0},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3}]},{\"Round\":7,\"isAttack\":true,\"Opps\":[{\"UUID\":\"\",\"Opp\":1021,\"Target\":null,\"Value\":-1},{\"UUID\":\"atk_10\",\"Opp\":1001,\"Target\":null,\"Value\":0},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3},{\"UUID\":\"atk_5\",\"Opp\":1030,\"Target\":[\"def\"],\"Value\":400},{\"UUID\":\"atk_5\",\"Opp\":1022,\"Target\":[\"def\"],\"Value\":-400},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3}]},{\"Round\":8,\"isAttack\":false,\"Opps\":[{\"UUID\":\"\",\"Opp\":1021,\"Target\":null,\"Value\":-1},{\"UUID\":\"def_4\",\"Opp\":1001,\"Target\":null,\"Value\":0},{\"UUID\":\"def_1\",\"Opp\":1002,\"Target\":null,\"Value\":0},{\"UUID\":\"def_6\",\"Opp\":1002,\"Target\":null,\"Value\":0},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3},{\"UUID\":\"def_1\",\"Opp\":11,\"Target\":[\"atk_5\"],\"Value\":null},{\"UUID\":\"def_1\",\"Opp\":1101,\"Target\":[\"atk_5\"],\"Value\":1},{\"UUID\":\"def_1\",\"Opp\":1030,\"Target\":[\"atk_5\"],\"Value\":287},{\"UUID\":\"def_1\",\"HP\":885,\"Opp\":1040,\"Target\":[\"atk_5\"],\"Value\":-287},{\"UUID\":\"def_6\",\"Opp\":53,\"Target\":[\"def\"],\"Value\":350},{\"UUID\":\"def_6\",\"Opp\":1022,\"Target\":[\"def\"],\"Value\":350},{\"UUID\":\"def_6\",\"Opp\":1030,\"Target\":[\"atk\"],\"Value\":220},{\"UUID\":\"def_6\",\"Opp\":1022,\"Target\":[\"atk\"],\"Value\":-220},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3}]},{\"Round\":9,\"isAttack\":true,\"Opps\":[{\"UUID\":\"\",\"Opp\":1021,\"Target\":null,\"Value\":-1},{\"UUID\":\"atk_1\",\"Opp\":1001,\"Target\":null,\"Value\":0},{\"UUID\":\"atk_6\",\"Opp\":1002,\"Target\":null,\"Value\":0},{\"UUID\":\"atk_4\",\"Opp\":1002,\"Target\":null,\"Value\":0},{\"UUID\":\"atkrune_14\",\"Opp\":26,\"Target\":[],\"Value\":244},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3},{\"UUID\":\"atk_5\",\"Opp\":1101,\"Target\":[\"atk_5\"],\"Value\":0},{\"UUID\":\"atk_6\",\"Opp\":1030,\"Target\":[\"def_6\"],\"Value\":495},{\"UUID\":\"atk_6\",\"HP\":998,\"Opp\":1040,\"Target\":[\"def_6\"],\"Value\":-495},{\"UUID\":\"atk_6\",\"Opp\":95,\"Target\":[\"def\"],\"Value\":371},{\"UUID\":\"atk_6\",\"Opp\":1022,\"Target\":[\"def\"],\"Value\":-371},{\"UUID\":\"atk_4\",\"Opp\":1030,\"Target\":[\"def\"],\"Value\":530},{\"UUID\":\"atk_4\",\"Opp\":1022,\"Target\":[\"def\"],\"Value\":-530},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3}]},{\"Round\":10,\"isAttack\":false,\"Opps\":[{\"UUID\":\"\",\"Opp\":1021,\"Target\":null,\"Value\":-1},{\"UUID\":\"def_5\",\"Opp\":1001,\"Target\":null,\"Value\":0},{\"UUID\":\"def_8\",\"Opp\":1002,\"Target\":null,\"Value\":0},{\"UUID\":\"def_8\",\"Opp\":28,\"Target\":[[\"def_6\"]],\"Value\":125},{\"UUID\":\"def_8\",\"Opp\":1040,\"Target\":[\"def_6\"],\"Value\":125},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3},{\"UUID\":\"def_1\",\"Opp\":11,\"Target\":[\"atk_5\"],\"Value\":null},{\"UUID\":\"def_1\",\"Opp\":1101,\"Target\":[\"atk_5\"],\"Value\":1},{\"UUID\":\"def_1\",\"Opp\":1030,\"Target\":[\"atk_5\"],\"Value\":287},{\"UUID\":\"def_1\",\"Opp\":25,\"Target\":[\"atk_5\"],\"Value\":-180},{\"UUID\":\"def_1\",\"HP\":598,\"Opp\":1040,\"Target\":[\"atk_5\"],\"Value\":-107},{\"UUID\":\"def_6\",\"Opp\":53,\"Target\":[\"def\"],\"Value\":350},{\"UUID\":\"def_6\",\"Opp\":1022,\"Target\":[\"def\"],\"Value\":350},{\"UUID\":\"def_6\",\"Opp\":1030,\"Target\":[\"atk_6\"],\"Value\":220},{\"UUID\":\"atk_6\",\"Opp\":71,\"Target\":[\"atk_6\"],\"Value\":120},{\"UUID\":\"def_6\",\"Opp\":25,\"Target\":[\"atk_6\"],\"Value\":-120},{\"UUID\":\"def_6\",\"HP\":1220,\"Opp\":1040,\"Target\":[\"atk_6\"],\"Value\":0},{\"UUID\":\"def_8\",\"Opp\":1030,\"Target\":[\"atk_4\"],\"Value\":359},{\"UUID\":\"def_8\",\"Opp\":25,\"Target\":[\"atk_4\"],\"Value\":-180},{\"UUID\":\"def_8\",\"HP\":1100,\"Opp\":1040,\"Target\":[\"atk_4\"],\"Value\":-179},{\"UUID\":\"atk_4\",\"Opp\":58,\"Target\":[\"atk_4\"],\"Value\":50},{\"UUID\":\"atk_4\",\"Opp\":1020,\"Target\":[\"atk_4\"],\"Value\":50},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3}]},{\"Round\":11,\"isAttack\":true,\"Opps\":[{\"UUID\":\"atkrune_14\",\"Opp\":26,\"Target\":[],\"Value\":-244},{\"UUID\":\"\",\"Opp\":1021,\"Target\":null,\"Value\":-1},{\"UUID\":\"atk_7\",\"Opp\":1001,\"Target\":null,\"Value\":0},{\"UUID\":\"atk_10\",\"Opp\":1002,\"Target\":null,\"Value\":0},{\"UUID\":\"atk_10\",\"Opp\":34,\"Target\":[\"atk_5\",\"atk_6\",\"atk_4\"],\"Value\":175},{\"UUID\":\"atk_10\",\"Opp\":1020,\"Target\":[\"atk_5\"],\"Value\":175},{\"UUID\":\"atk_10\",\"Opp\":1020,\"Target\":[\"atk_6\"],\"Value\":175},{\"UUID\":\"atk_10\",\"Opp\":1020,\"Target\":[\"atk_4\"],\"Value\":175},{\"UUID\":\"atkrune_14\",\"Opp\":26,\"Target\":[],\"Value\":244},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3},{\"UUID\":\"atk_5\",\"Opp\":1101,\"Target\":[\"atk_5\"],\"Value\":0},{\"UUID\":\"atk_6\",\"Opp\":1030,\"Target\":[\"def_6\"],\"Value\":670},{\"UUID\":\"atk_6\",\"HP\":628,\"Opp\":1040,\"Target\":[\"def_6\"],\"Value\":-628},{\"UUID\":\"atk_6\",\"Opp\":95,\"Target\":[\"def\"],\"Value\":471},{\"UUID\":\"atk_6\",\"Opp\":1022,\"Target\":[\"def\"],\"Value\":-471},{\"UUID\":\"def_6\",\"Opp\":1003,\"Target\":null,\"Value\":0},{\"UUID\":\"atk_4\",\"Opp\":94,\"Target\":[\"atk_4\"],\"Value\":120},{\"UUID\":\"atk_4\",\"Opp\":1020,\"Target\":[\"atk_4\"],\"Value\":906},{\"UUID\":\"atk_4\",\"Opp\":1030,\"Target\":[\"def_8\"],\"Value\":1661},{\"UUID\":\"atk_4\",\"Opp\":25,\"Target\":[\"def_8\"],\"Value\":-100},{\"UUID\":\"atk_4\",\"HP\":1112,\"Opp\":1040,\"Target\":[\"def_8\"],\"Value\":-1112},{\"UUID\":\"atk_4\",\"Opp\":5,\"Target\":[\"atk_4\"],\"Value\":179},{\"UUID\":\"atk_4\",\"Opp\":1040,\"Target\":[\"atk_4\"],\"Value\":179},{\"UUID\":\"def_8\",\"Opp\":1003,\"Target\":null,\"Value\":0},{\"UUID\":\"atk_4\",\"Opp\":1020,\"Target\":[\"atk_4\"],\"Value\":-906},{\"UUID\":\"atk_10\",\"Opp\":20,\"Target\":[\"def_1\"],\"Value\":150},{\"UUID\":\"atk_10\",\"Opp\":1040,\"Target\":[\"def_1\"],\"Value\":-204},{\"UUID\":\"atk_10\",\"Opp\":1030,\"Target\":[\"def\"],\"Value\":515},{\"UUID\":\"atk_10\",\"Opp\":1022,\"Target\":[\"def\"],\"Value\":-515},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3}]},{\"Round\":12,\"isAttack\":false,\"Opps\":[{\"UUID\":\"\",\"Opp\":1021,\"Target\":null,\"Value\":-1},{\"UUID\":\"def_7\",\"Opp\":1001,\"Target\":null,\"Value\":0},{\"UUID\":\"def_4\",\"Opp\":1002,\"Target\":null,\"Value\":0},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3},{\"UUID\":\"def_1\",\"Opp\":11,\"Target\":[\"atk_10\"],\"Value\":null},{\"UUID\":\"def_1\",\"Opp\":1101,\"Target\":[\"atk_10\"],\"Value\":1},{\"UUID\":\"def_1\",\"Opp\":1030,\"Target\":[\"atk_5\"],\"Value\":287},{\"UUID\":\"def_1\",\"Opp\":25,\"Target\":[\"atk_5\"],\"Value\":-180},{\"UUID\":\"def_1\",\"HP\":491,\"Opp\":1040,\"Target\":[\"atk_5\"],\"Value\":-107},{\"UUID\":\"def_4\",\"Opp\":15,\"Target\":[\"atk_10\"],\"Value\":80},{\"UUID\":\"def_4\",\"Opp\":1103,\"Target\":[\"atk_10\"],\"Value\":1},{\"UUID\":\"def_4\",\"Opp\":1040,\"Target\":[\"atk_10\"],\"Value\":-80},{\"UUID\":\"def_4\",\"Opp\":1030,\"Target\":[\"atk_6\"],\"Value\":264},{\"UUID\":\"atk_6\",\"Opp\":71,\"Target\":[\"atk_6\"],\"Value\":120},{\"UUID\":\"def_4\",\"Opp\":25,\"Target\":[\"atk_6\"],\"Value\":-120},{\"UUID\":\"def_4\",\"HP\":1220,\"Opp\":1040,\"Target\":[\"atk_6\"],\"Value\":0},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3}]},{\"Round\":13,\"isAttack\":true,\"Opps\":[{\"UUID\":\"atkrune_14\",\"Opp\":26,\"Target\":[],\"Value\":-244},{\"UUID\":\"\",\"Opp\":1021,\"Target\":null,\"Value\":-1},{\"UUID\":\"atk_3\",\"Opp\":1001,\"Target\":null,\"Value\":0},{\"UUID\":\"atk_1\",\"Opp\":1002,\"Target\":null,\"Value\":0},{\"UUID\":\"atkrune_14\",\"Opp\":26,\"Target\":[],\"Value\":244},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3},{\"UUID\":\"atk_5\",\"Opp\":23,\"Target\":[\"def_1\",\"def_4\"],\"Value\":40},{\"UUID\":\"atk_5\",\"Opp\":1040,\"Target\":[\"def_1\"],\"Value\":-40},{\"UUID\":\"atk_5\",\"Opp\":1104,\"Target\":[\"def_1\"],\"Value\":1},{\"UUID\":\"atk_5\",\"Opp\":1040,\"Target\":[\"def_4\"],\"Value\":-40},{\"UUID\":\"atk_5\",\"Opp\":1104,\"Target\":[\"def_4\"],\"Value\":1},{\"UUID\":\"atk_5\",\"Opp\":11,\"Target\":[\"def_1\",\"def_4\"],\"Value\":null},{\"UUID\":\"atk_5\",\"Opp\":1101,\"Target\":[\"def_1\"],\"Value\":1},{\"UUID\":\"atk_5\",\"Opp\":1101,\"Target\":[\"def_4\"],\"Value\":1},{\"UUID\":\"atk_5\",\"Opp\":1030,\"Target\":[\"def_1\"],\"Value\":575},{\"UUID\":\"atk_5\",\"HP\":844,\"Opp\":1040,\"Target\":[\"def_1\"],\"Value\":-575},{\"UUID\":\"atk_6\",\"Opp\":1030,\"Target\":[\"def_4\"],\"Value\":670},{\"UUID\":\"atk_6\",\"HP\":877,\"Opp\":1040,\"Target\":[\"def_4\"],\"Value\":-670},{\"UUID\":\"atk_6\",\"Opp\":95,\"Target\":[\"def\"],\"Value\":502},{\"UUID\":\"atk_6\",\"Opp\":1022,\"Target\":[\"def\"],\"Value\":-502},{\"UUID\":\"atk_4\",\"Opp\":1030,\"Target\":[\"def\"],\"Value\":755},{\"UUID\":\"atk_4\",\"Opp\":1022,\"Target\":[\"def\"],\"Value\":-755},{\"UUID\":\"atk_10\",\"Opp\":1101,\"Target\":[\"atk_10\"],\"Value\":0},{\"UUID\":\"atk_10\",\"Opp\":1103,\"Target\":[\"atk_10\"],\"Value\":0},{\"UUID\":\"atk_1\",\"Opp\":28,\"Target\":[[\"atk_5\",\"atk_10\"]],\"Value\":200},{\"UUID\":\"atk_1\",\"Opp\":1040,\"Target\":[\"atk_5\"],\"Value\":200},{\"UUID\":\"atk_1\",\"Opp\":1040,\"Target\":[\"atk_10\"],\"Value\":80},{\"UUID\":\"atk_1\",\"Opp\":1030,\"Target\":[\"def\"],\"Value\":605},{\"UUID\":\"atk_1\",\"Opp\":1022,\"Target\":[\"def\"],\"Value\":-605},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3}]},{\"Round\":14,\"isAttack\":false,\"Opps\":[{\"UUID\":\"\",\"Opp\":1021,\"Target\":null,\"Value\":-1},{\"UUID\":\"def_2\",\"Opp\":1001,\"Target\":null,\"Value\":0},{\"UUID\":\"defrune_9\",\"Opp\":22,\"Target\":[\"atk_5\"],\"Value\":180},{\"UUID\":\"defrune_9\",\"Opp\":1040,\"Target\":[\"atk_5\"],\"Value\":-180},{\"UUID\":\"defrune_9\",\"Opp\":1104,\"Target\":[\"atk_5\"],\"Value\":1},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3},{\"UUID\":\"def_1\",\"Opp\":1101,\"Target\":[\"def_1\"],\"Value\":0},{\"UUID\":\"def_1\",\"Opp\":1104,\"Target\":[\"def_1\"],\"Value\":0},{\"UUID\":\"def_1\",\"Opp\":1040,\"Target\":[\"def_1\"],\"Value\":-40},{\"UUID\":\"def_4\",\"Opp\":1101,\"Target\":[\"def_4\"],\"Value\":0},{\"UUID\":\"def_4\",\"Opp\":1104,\"Target\":[\"def_4\"],\"Value\":0},{\"UUID\":\"def_4\",\"Opp\":1040,\"Target\":[\"def_4\"],\"Value\":-40},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3}]},{\"Round\":15,\"isAttack\":true,\"Opps\":[{\"UUID\":\"atkrune_14\",\"Opp\":26,\"Target\":[],\"Value\":-244},{\"UUID\":\"\",\"Opp\":1021,\"Target\":null,\"Value\":-1},{\"UUID\":\"atk_8\",\"Opp\":1001,\"Target\":null,\"Value\":0},{\"UUID\":\"atk_7\",\"Opp\":1002,\"Target\":null,\"Value\":0},{\"UUID\":\"atkrune_11\",\"Opp\":17,\"Target\":[\"def_1\",\"def_4\"],\"Value\":180},{\"UUID\":\"atkrune_11\",\"Opp\":1040,\"Target\":[\"def_1\"],\"Value\":-180},{\"UUID\":\"atkrune_11\",\"Opp\":1040,\"Target\":[\"def_4\"],\"Value\":-167},{\"UUID\":\"def_4\",\"Opp\":1003,\"Target\":null,\"Value\":0},{\"UUID\":\"atkrune_14\",\"Opp\":26,\"Target\":[],\"Value\":244},{\"UUID\":\"\",\"Opp\":1060,\"Target\":[],\"Value\":3},{\"UUID\":\"atk_5\",\"Opp\":23,\"Target\":[\"def_1\"],\"Value\":40},{\"UUID\":\"atk_5\",\"Opp\":1040,\"Target\":[\"def_1\"],\"Value\":-40},{\"UUID\":\"atk_5\",\"Opp\":1104,\"Target\":[\"def_1\"],\"Value\":1},{\"UUID\":\"atk_5\",\"Opp\":11,\"Target\":[\"def_1\"],\"Value\":null},{\"UUID\":\"atk_5\",\"Opp\":1101,\"Target\":[\"def_1\"],\"Value\":1},{\"UUID\":\"atk_5\",\"Opp\":1030,\"Target\":[\"def_1\"],\"Value\":575},{\"UUID\":\"atk_5\",\"HP\":9,\"Opp\":1040,\"Target\":[\"def_1\"],\"Value\":-9},{\"UUID\":\"def_1\",\"Opp\":1003,\"Target\":null,\"Value\":0},{\"UUID\":\"atk_5\",\"Opp\":1104,\"Target\":[\"atk_5\"],\"Value\":0},{\"UUID\":\"atk_5\",\"Opp\":1040,\"Target\":[\"atk_5\"],\"Value\":-180},{\"UUID\":\"atk_6\",\"Opp\":1030,\"Target\":[\"def\"],\"Value\":670},{\"UUID\":\"atk_6\",\"Opp\":1022,\"Target\":[\"def\"],\"Value\":-670},{\"UUID\":\"atk_4\",\"Opp\":1030,\"Target\":[\"def\"],\"Value\":755},{\"UUID\":\"atk_4\",\"Opp\":1022,\"Target\":[\"def\"],\"Value\":-181}]}]},\"version\":{\"http\":\"201302090\",\"stop\":\"\",\"appversion\":\"version_1\",\"appurl\":\"ios://xxx\"}}\n";
//    MazeBattleResponse b = new Gson().fromJson(a, MazeBattleResponse.class);
    System.out.println("{\"status\":0,\"message\":\"\u4eca\u65e5\u8d60\u9001\u8fbe\u5230\u4e0a\u9650\uff01\",\"type\":0}");
    System.currentTimeMillis();
  }
}
