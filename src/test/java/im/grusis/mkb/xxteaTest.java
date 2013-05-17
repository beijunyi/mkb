package im.grusis.mkb;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: Mothership
 * Date: 13-5-12
 * Time: 下午7:49
 */
public class xxteaTest {

  @Test
  public void intArrayAndStringConversion() {
    String string = "This is a string";

    int[] intArray = XXTEA.stringToIntArray(string, false);
    Assert.assertEquals(new int[] {1936287828, 544434464, 1953701985, 1735289202}, intArray);
    String result = XXTEA.intArrayToString(intArray, false);
    Assert.assertEquals(string, result);

    intArray = XXTEA.stringToIntArray(string, true);
    Assert.assertEquals(new int[]{1936287828, 544434464, 1953701985, 1735289202, 16}, intArray);
    result = XXTEA.intArrayToString(intArray, true);
    Assert.assertEquals(string, result);

    string = "This_Is_Another_String";
    intArray = XXTEA.stringToIntArray(string, false);
    Assert.assertEquals(new int[] {1936287828, 1601390943, 1953459777, 1601332584, 1769108563, 26478}, intArray);
    result = XXTEA.intArrayToString(intArray, false);
    Assert.assertEquals(string, result.trim());

    intArray = XXTEA.stringToIntArray(string, true);
    Assert.assertEquals(new int[]{1936287828, 1601390943, 1953459777, 1601332584, 1769108563, 26478, 22}, intArray);
    result = XXTEA.intArrayToString(intArray, true);
    Assert.assertEquals(string, result);

    intArray = new int[] {744133714,1442689526,-1756678249,1958960298,1666444042,363532449,1522869684,326129130,2068295067,1200958916,-156314226,-561103368,-2071354929,1097447860,-1194902372,331116709,-1115683497,548071681,-426251272,-74582883,875446691,714751630,82098821,-847650081,-1749939724,1243804985,374440886,-2032348385,581718788,-709063459,-1058867791,-1300666635,-224557936,-2041208534,1626328706,-1276874915,-1971917144,-1377005881,564743884,-1869042337,-1239702562,-339728249,-590217249,-1720385703,1036858700,-197820740,-611985403,1236411203,1158764078,2117499185,861590659,-1172557075,-227914519,454189855,564114306,-426593762,-264594683,1075308504,-1351658083,-1281462289,-1267648484,-2061471489,948856181,-1184832680,1871284485,1858951503,-1833186945,-1402497910,-167953108,-1570107053,2007063943,2001377439,1925243854,103788792,1245799116,-407942204,-1243978550,-504115862,-1992462418,-1371849562,278342439,1363581419,422682264,1809843165,1247974779,1859941916,-240847594,87790978,2011729953,-147122884,-848108561,424567724,1557189572,-1191293550,-1429013802,-1280660980,1869616391,-1637406431,-1289931373,-409350228,-2072547091,210828827,455389594,-1760523971,852589439,1501073622,-1480947522,-493884235,-1635600829,580178911,-1149768879,1656104910,130061653,-2115959001,-957002125,417761321,1756712318,-260210347,2111007826,-1491237823,-280365993,1593147963,-643976042,-583164473,-1445438526,-854582715,-1114293592,496418864,1067888436,1114478452,1453319463,1580470845,-1003183404,-971409011,1031181865,642741346,373206076,1010365564,1704905704,655959707,33273443,1460934524,-1756403111,-44425938,1338405859,-1226582517,-490708939,391313029,-1824853508,-259828963,1439651415,289519162,1809170200,-611194535,2128034720,-759115246,-406098580,397202635,-1369202188,787246090,1407891945,1082180766,-1086512126,-2061415091,531853075,1366601306,-1654047701,894909836,-543450280,970807968,929339641,1078911611,-588645539,942170458,-475107792,1957926687,-1803733650,1426649356,-1793549162,436986487,-291907861,653890303,-2089284143,1667754641,1500927675,1847793388,-2107812469,118722642,-1624508380,467650832,-554867681,1873438819,721111630,-1550654253,308639407,2000126354,-1801030244,1249192847,-916757520,-2061392939,-2079098952,918168263,1313978583,1764059125,-2119968152,151159452,-2075125413,-1106797477,-1138499147,518616821,-1560718071,1042914179,-1771528338,1407072132,22380948,169410910,-1851881463,-810179822,-677972887,-1767165469,-506719146,362216931,-14735688,1868259790,-1492133091,1498020926,-1968568091,1481534940,-79383456,456051547,-572521604,655958197,1800606279,307696164,-2120212496,1128323170,984739630,1328970360,-1660039484,1401918040,1642030649,-1778381295,-1051320073,761390398,1289324928,-912457858,-1856762852,582377470,72030654,273293567,1624704130,-662206758,-1131116162,116943916,-80006578,1570229275,-1367525759,1644944479,-730550606,-190389575,-116745023,-619659479,-416240199,1798088320,1974424612,-838689416,-1660201864,-1455074061,1235478801,329345190,1249854743,414118263,942427599,1771915020,-2806937,-947914236,-490072038,-1529114005,1530437213,63944927,-1398693732,2100915698,-1393975347,54973377,1865398814,1444114098,-1359893375,1819696226,233101853,1992780528,879200200,-920185280,1263377444,380862697,-1001495392,-1028975346,-1305194670,298873576,1948685096,-1096910569,-1476622515,-607112577,15335139,-38819569,702176418,133410700,129092357,521442555,1780987800,1595339533,1647257965,400222372,-517433211,1654482590,-2133707987,-1617397291,1564623771,148944640,1107228885,-813651450,-147973086,656158682,-662867456,-2037845622,-2001726289,-280983062,1704721673,-167319440,86949595,1614497953,830254904,827317018,2125060386,-458420049,-1280218804,914086180,1630726667,-4987280,-879317140,139301532,-615189669,97290544,-1942959423,-1663940083,-519718274,1137336633,1457109448,841504027,1138788284,-617794382,2027526731,-955788345,-1047766000,997694443,1458538190,-89857598,628818742,-1751140964,-2089614203,-1737850660,1239539149,1608029526,2141886674,792936867,-1614009815,473684572,-1673939274,-803183873,-1368064358,-1966183318,229052582,1257060523,-751968587,-2083220397,-41857786,-1234299861,-423199684,-1275865523,-1898511547,-134617971,-216644846,769897091,1449814634,1649359366,857471182,-593288166,-2067914518,-1328880795,-795640690,-693804503,1201895475,-81701153,-3689315,-7773140,2105635888,265935989,382346631,1629284421,-1728355210,-402507692,68376920,857706682,-1102126869,1040074366,929293283,-1138366561,477474516,-1309694875,-318613784,-1292300270,-2035686301,1570531771,1979241264,-1941180278,731054466,-830714200,2137961420,-497704452,761262808,268209376,1505749864,1551365943,1765973965,1496226583,437012257,1490463044,-575781356,2109619242,-2081133852,1884005012,2031456503,-1102828786,-1476113823,-27354564,-795208549,1270439664,2042740554,-1478503228,232072219,-2139085039,-385569721,594478380,-1017945889,1764273446,1003069063,-528074739,1676315048,229114005,1169328485,-951646265,-107448295,466359861,343319804,-651900443,-843607549,-2014309499,973007946,2092874077,-1861742573,-166169620,-2022336910,-1686953188,-582909922,954214419,1203905092,-2045036858,-1074090489,2120845582,626974528,160517803,724957957,1939441522,369735053,-1722636994,351520526,-1586132305,949557269,236883707,-103024585,1556708666,-1389038211,-1413682157,1479509178,-723461026,63424744,-544972373,549282674,849197450,-1201857122,-1756146167,403887526,511966996,1790682692,-1039988680,1064027002,-120613230,1275821143,-1962297673,453099903,-691753803,2053801167,1889350465,-678348971,671818585,1839926318,-509873020,1254645180,2025269449,1906500881,-1682539943,-1101104783,-1172403364,1911399762,1987240839,95971067,-1664709061,1770072643,1803140661,-815951977,634669237,-821051950,1065637361,-1778901505,911553788,-536370568,1933392253,1535549264,-72199364,1349923745,1690322983,-1039969827,533184626,1036203061,566356966,332983146,1916461341,847425945,1245216155,-853965507,-675653173,-2007500236,-1519336279,1643374115,-277527035,-133725705,-1945578866,-69101858,-759841646,-1085668979,-545943786,902384290,-1373699987,666740033,1088878028,1758235320,-1423721718,-1784834703,167942955,-1896733950,-201339102,-692542113,-1387271008,202513375,-991132015,327870573,-844120845,-2062194287,1428902990,471408576,690806947,2048723727,-1195161150,257015806,1023479321,209811324,1612946771,131539911,-926627449,-1509195557,-11567249,1900978790,-1284044588,-1991361156,-1347608398,-1669412309,152141438,-857680444,1005472655,-1811098312,-318812158,1645056658,-2082414020,972208004,617560574,671903724,-1109574979,813992156,660490815,-1299391887,1398506461,-1211331745,2026229722,-1241645524,-1285268656,170903239,96253101,2004509992,1458745036,1947582424,-1732171167,2050619705,-11803514,-1892763303,159461989,1218430467,-503423894,-1350014057,41374752,-1654609485,-741346171,-1666544223,1723117264,-2044499212,-1111784018,378804143,-1164619247,1313721700,1173600525,776002706,-1356281725,440604228,366973859,2136471626,-562620665,-887595866,546296996,825393639,-2135766674,-115021234,-1554905913,-855211615,-470273551,-20328453,1633525669,-368396059,-1593572143,894743447,389498570,-1910841538,-2124526972,-1290768891,1654623931,-666762300,-2112541426,1551482486,-2126024019,1330411615,-1769881512,-1234087537,-2107495931,27050368,-2013635419,564077944,-136979249,-1345987778,909306308,1793461673,1617549825,-2106882747,90580395,915055280,780717985,421068968,2082224370,904316835,-495270967,1643763458,178354673,-232544627,858659527,-2018140096,1381791745,1374706935,-1041492282,-1314480454,31326967,-1937439601,1946145553,-2013412409,-665425232,262751749,-1756119632,-139770883,-1013003995,1296978368,-506809410,2131516622,-2075474472,844339737,1678451593,-1666206505,1456114823,-1168824292,1827121497,1467907734,1214292462,1807171038,-1370061671,634301309,-1314042513,67855002,-1551831694,2121718860,1542456608,1609860446,1551210529,2104976546,-278105391,357439516,-1015897824,319475017,-977175200,-30903717,-1928467786,2022894607,-1279592669,-2097338640,-2065703133,-420429883,789078652,656911641,-1708308474,1944481694,1598624058,-1797234132,-1535812239,-803972655,-1392148409,1248224649,1320217668,-1446142711,-1223238135,-27967733,-1817066541,-1032321518,850207118,696070857,-25595447,-677040620,-1605371080,-17905539,274285736,778777887,1656526097,398851213,965765695,-900885274,-185500906,-581484591,-1011732504,-1679235056,614785801,336268994,-1123850099,-1083763211,-509110244,830483568,2028831612,-1070353382,-216576849,-323967554,884995296,781353540,1941077320,825675746,-1752077919,-804915572,1385243913,-1877306106,865786139,-1382406665,629057703,1843986902,86772408,-704101888,1094527531,-1243811941,-1317800301,-1404120646,-481026989,-1810393685,2047868265,1334652815,1356086869,1288963841,-38976844,108219325,-842880664,1455956076,-588573572,302336659,-1447688818,112063662,75328478,1797643872,2065431861,-549916900,1947161614,1593174884,-146729032,400349364,-1675029256,768273224,-799618605,-919804864,1150369407,-376483092,1912163674,-1941295928,217424870,-4007248,152518474,1836875060,1123482801,1052693623,860643101,1682977715,-531693745,-1710357136,-1735479892,1916540721,1990075094,125365145,-589507080,1638846465,1586650166,-1108105620,915132667,934096060,-596196080,-842225661,1532846080,-1226544654,1596899141,-2023731848,-878205476,-225193958,-677477456,1323641534,921246628,-578260697,-925073985,1763856503,3412};
    result = XXTEA.intArrayToString(intArray, false);
    Assert.assertEquals(intArray, XXTEA.stringToIntArray(result, false));
  }

  @Test
  public void encryptAndDecrypt() {
    String string = "to be encrypted";
    String key = "key";
    int[] encryptedIntArray = XXTEA.stringToIntArray(XXTEA.encrypt(string, key), false);
    Assert.assertEquals(new int[] {1202067163, 564852801, -522773858, 1633597383, -1397140425}, encryptedIntArray);

    string = "RAW_STRING";
    key = "another key";
    encryptedIntArray = XXTEA.stringToIntArray(XXTEA.encrypt(string, key), false);
    Assert.assertEquals(new int[] {318749921, 1024289719, 544685602, -608595361}, encryptedIntArray);

    int[] intArray = new int[] {744133714,1442689526,-1756678249,1958960298,1666444042,363532449,1522869684,326129130,2068295067,1200958916,-156314226,-561103368,-2071354929,1097447860,-1194902372,331116709,-1115683497,548071681,-426251272,-74582883,875446691,714751630,82098821,-847650081,-1749939724,1243804985,374440886,-2032348385,581718788,-709063459,-1058867791,-1300666635,-224557936,-2041208534,1626328706,-1276874915,-1971917144,-1377005881,564743884,-1869042337,-1239702562,-339728249,-590217249,-1720385703,1036858700,-197820740,-611985403,1236411203,1158764078,2117499185,861590659,-1172557075,-227914519,454189855,564114306,-426593762,-264594683,1075308504,-1351658083,-1281462289,-1267648484,-2061471489,948856181,-1184832680,1871284485,1858951503,-1833186945,-1402497910,-167953108,-1570107053,2007063943,2001377439,1925243854,103788792,1245799116,-407942204,-1243978550,-504115862,-1992462418,-1371849562,278342439,1363581419,422682264,1809843165,1247974779,1859941916,-240847594,87790978,2011729953,-147122884,-848108561,424567724,1557189572,-1191293550,-1429013802,-1280660980,1869616391,-1637406431,-1289931373,-409350228,-2072547091,210828827,455389594,-1760523971,852589439,1501073622,-1480947522,-493884235,-1635600829,580178911,-1149768879,1656104910,130061653,-2115959001,-957002125,417761321,1756712318,-260210347,2111007826,-1491237823,-280365993,1593147963,-643976042,-583164473,-1445438526,-854582715,-1114293592,496418864,1067888436,1114478452,1453319463,1580470845,-1003183404,-971409011,1031181865,642741346,373206076,1010365564,1704905704,655959707,33273443,1460934524,-1756403111,-44425938,1338405859,-1226582517,-490708939,391313029,-1824853508,-259828963,1439651415,289519162,1809170200,-611194535,2128034720,-759115246,-406098580,397202635,-1369202188,787246090,1407891945,1082180766,-1086512126,-2061415091,531853075,1366601306,-1654047701,894909836,-543450280,970807968,929339641,1078911611,-588645539,942170458,-475107792,1957926687,-1803733650,1426649356,-1793549162,436986487,-291907861,653890303,-2089284143,1667754641,1500927675,1847793388,-2107812469,118722642,-1624508380,467650832,-554867681,1873438819,721111630,-1550654253,308639407,2000126354,-1801030244,1249192847,-916757520,-2061392939,-2079098952,918168263,1313978583,1764059125,-2119968152,151159452,-2075125413,-1106797477,-1138499147,518616821,-1560718071,1042914179,-1771528338,1407072132,22380948,169410910,-1851881463,-810179822,-677972887,-1767165469,-506719146,362216931,-14735688,1868259790,-1492133091,1498020926,-1968568091,1481534940,-79383456,456051547,-572521604,655958197,1800606279,307696164,-2120212496,1128323170,984739630,1328970360,-1660039484,1401918040,1642030649,-1778381295,-1051320073,761390398,1289324928,-912457858,-1856762852,582377470,72030654,273293567,1624704130,-662206758,-1131116162,116943916,-80006578,1570229275,-1367525759,1644944479,-730550606,-190389575,-116745023,-619659479,-416240199,1798088320,1974424612,-838689416,-1660201864,-1455074061,1235478801,329345190,1249854743,414118263,942427599,1771915020,-2806937,-947914236,-490072038,-1529114005,1530437213,63944927,-1398693732,2100915698,-1393975347,54973377,1865398814,1444114098,-1359893375,1819696226,233101853,1992780528,879200200,-920185280,1263377444,380862697,-1001495392,-1028975346,-1305194670,298873576,1948685096,-1096910569,-1476622515,-607112577,15335139,-38819569,702176418,133410700,129092357,521442555,1780987800,1595339533,1647257965,400222372,-517433211,1654482590,-2133707987,-1617397291,1564623771,148944640,1107228885,-813651450,-147973086,656158682,-662867456,-2037845622,-2001726289,-280983062,1704721673,-167319440,86949595,1614497953,830254904,827317018,2125060386,-458420049,-1280218804,914086180,1630726667,-4987280,-879317140,139301532,-615189669,97290544,-1942959423,-1663940083,-519718274,1137336633,1457109448,841504027,1138788284,-617794382,2027526731,-955788345,-1047766000,997694443,1458538190,-89857598,628818742,-1751140964,-2089614203,-1737850660,1239539149,1608029526,2141886674,792936867,-1614009815,473684572,-1673939274,-803183873,-1368064358,-1966183318,229052582,1257060523,-751968587,-2083220397,-41857786,-1234299861,-423199684,-1275865523,-1898511547,-134617971,-216644846,769897091,1449814634,1649359366,857471182,-593288166,-2067914518,-1328880795,-795640690,-693804503,1201895475,-81701153,-3689315,-7773140,2105635888,265935989,382346631,1629284421,-1728355210,-402507692,68376920,857706682,-1102126869,1040074366,929293283,-1138366561,477474516,-1309694875,-318613784,-1292300270,-2035686301,1570531771,1979241264,-1941180278,731054466,-830714200,2137961420,-497704452,761262808,268209376,1505749864,1551365943,1765973965,1496226583,437012257,1490463044,-575781356,2109619242,-2081133852,1884005012,2031456503,-1102828786,-1476113823,-27354564,-795208549,1270439664,2042740554,-1478503228,232072219,-2139085039,-385569721,594478380,-1017945889,1764273446,1003069063,-528074739,1676315048,229114005,1169328485,-951646265,-107448295,466359861,343319804,-651900443,-843607549,-2014309499,973007946,2092874077,-1861742573,-166169620,-2022336910,-1686953188,-582909922,954214419,1203905092,-2045036858,-1074090489,2120845582,626974528,160517803,724957957,1939441522,369735053,-1722636994,351520526,-1586132305,949557269,236883707,-103024585,1556708666,-1389038211,-1413682157,1479509178,-723461026,63424744,-544972373,549282674,849197450,-1201857122,-1756146167,403887526,511966996,1790682692,-1039988680,1064027002,-120613230,1275821143,-1962297673,453099903,-691753803,2053801167,1889350465,-678348971,671818585,1839926318,-509873020,1254645180,2025269449,1906500881,-1682539943,-1101104783,-1172403364,1911399762,1987240839,95971067,-1664709061,1770072643,1803140661,-815951977,634669237,-821051950,1065637361,-1778901505,911553788,-536370568,1933392253,1535549264,-72199364,1349923745,1690322983,-1039969827,533184626,1036203061,566356966,332983146,1916461341,847425945,1245216155,-853965507,-675653173,-2007500236,-1519336279,1643374115,-277527035,-133725705,-1945578866,-69101858,-759841646,-1085668979,-545943786,902384290,-1373699987,666740033,1088878028,1758235320,-1423721718,-1784834703,167942955,-1896733950,-201339102,-692542113,-1387271008,202513375,-991132015,327870573,-844120845,-2062194287,1428902990,471408576,690806947,2048723727,-1195161150,257015806,1023479321,209811324,1612946771,131539911,-926627449,-1509195557,-11567249,1900978790,-1284044588,-1991361156,-1347608398,-1669412309,152141438,-857680444,1005472655,-1811098312,-318812158,1645056658,-2082414020,972208004,617560574,671903724,-1109574979,813992156,660490815,-1299391887,1398506461,-1211331745,2026229722,-1241645524,-1285268656,170903239,96253101,2004509992,1458745036,1947582424,-1732171167,2050619705,-11803514,-1892763303,159461989,1218430467,-503423894,-1350014057,41374752,-1654609485,-741346171,-1666544223,1723117264,-2044499212,-1111784018,378804143,-1164619247,1313721700,1173600525,776002706,-1356281725,440604228,366973859,2136471626,-562620665,-887595866,546296996,825393639,-2135766674,-115021234,-1554905913,-855211615,-470273551,-20328453,1633525669,-368396059,-1593572143,894743447,389498570,-1910841538,-2124526972,-1290768891,1654623931,-666762300,-2112541426,1551482486,-2126024019,1330411615,-1769881512,-1234087537,-2107495931,27050368,-2013635419,564077944,-136979249,-1345987778,909306308,1793461673,1617549825,-2106882747,90580395,915055280,780717985,421068968,2082224370,904316835,-495270967,1643763458,178354673,-232544627,858659527,-2018140096,1381791745,1374706935,-1041492282,-1314480454,31326967,-1937439601,1946145553,-2013412409,-665425232,262751749,-1756119632,-139770883,-1013003995,1296978368,-506809410,2131516622,-2075474472,844339737,1678451593,-1666206505,1456114823,-1168824292,1827121497,1467907734,1214292462,1807171038,-1370061671,634301309,-1314042513,67855002,-1551831694,2121718860,1542456608,1609860446,1551210529,2104976546,-278105391,357439516,-1015897824,319475017,-977175200,-30903717,-1928467786,2022894607,-1279592669,-2097338640,-2065703133,-420429883,789078652,656911641,-1708308474,1944481694,1598624058,-1797234132,-1535812239,-803972655,-1392148409,1248224649,1320217668,-1446142711,-1223238135,-27967733,-1817066541,-1032321518,850207118,696070857,-25595447,-677040620,-1605371080,-17905539,274285736,778777887,1656526097,398851213,965765695,-900885274,-185500906,-581484591,-1011732504,-1679235056,614785801,336268994,-1123850099,-1083763211,-509110244,830483568,2028831612,-1070353382,-216576849,-323967554,884995296,781353540,1941077320,825675746,-1752077919,-804915572,1385243913,-1877306106,865786139,-1382406665,629057703,1843986902,86772408,-704101888,1094527531,-1243811941,-1317800301,-1404120646,-481026989,-1810393685,2047868265,1334652815,1356086869,1288963841,-38976844,108219325,-842880664,1455956076,-588573572,302336659,-1447688818,112063662,75328478,1797643872,2065431861,-549916900,1947161614,1593174884,-146729032,400349364,-1675029256,768273224,-799618605,-919804864,1150369407,-376483092,1912163674,-1941295928,217424870,-4007248,152518474,1836875060,1123482801,1052693623,860643101,1682977715,-531693745,-1710357136,-1735479892,1916540721,1990075094,125365145,-589507080,1638846465,1586650166,-1108105620,915132667,934096060,-596196080,-842225661,1532846080,-1226544654,1596899141,-2023731848,-878205476,-225193958,-677477456,1323641534,921246628,-578260697,-925073985,1763856503,3412};
    string = XXTEA.intArrayToString(intArray, false);
    String encryptedString = XXTEA.encrypt(string, key);
    encryptedIntArray = new int[] {-2065748652, -40733323, -525434376, 1349861442, -331624399, -1698083258, -249342256, 2064907071, -13395384, -530289918, -1056341336, 254428119, -1711904319, 157326007, 1707445235, 1512400381, -321319029, -1374472119, 839026743, -1116507549, 1556170314, -135419404, 927872113, 1665413825, -544269622, 1079838762, 1670992882, 178054987, -783097427, -815404747, -807620171, 1290913279, 2128840861, -1413015093, 362718475, 1429958397, -1522050257, 1172300968, -793105710, 363574382, -130680906, 28729690, -345872774, -440441832, -1334624044, 702214356, -61178052, -1172129451, 1082439593, -1063674882, -1723271513, 2098017192, -34507156, -1160176639, -270421013, 2119110467, -39615651, -988148075, -404171496, 667420544, -14460124, -231188694, -1106966621, 2118100979, -1034719729, 1820489405, 35975682, -1270603779, -562729781, 1293824542, 1883427391, -2137664455, 1679944283, -1046137749, -137004755, 1982839860, -1905412677, 1791997259, 771462909, 1663609504, 1525699730, 1510479501, -535893121, 873736771, -1793275094, 1818006137, -1704394583, -1243289198, 1074884866, 1255687349, 700365950, 57459459, 1170060946, -2060981526, 1285389209, -1557668726, -766023656, -1866310887, 1548258409, 391755807, -1505124047, -1756892453, -1467189505, 1193039490, 319369309, -304098200, 1627660467, -179271094, -1045637772, 1509015400, -538747151, -174170262, -1241236129, 1048949778, -2137488819, 1673714976, 1453738991, 819150148, -301233646, -225817273, -632312216, -268809173, -1986075212, 1180121567, 196770020, 842383315, -1546858141, 342173481, -1659677191, 1860137853, 753741826, 828413033, -240103601, -1286452809, -486668022, -89879035, -1692927690, 1353982461, -957643884, 743091662, -33719714, -857381293, 11919206, 1827913745, -1432525680, -1432079641, -529993600, 735851629, 36913996, 42087514, 1368274897, 1240347984, 887367984, -1858102495, -906013252, 339158976, -1460775331, 21231428, -633711817, -756457763, 1914299781, 1033449220, 1967415313, -725033978, -1059030242, -1603652640, -1953240785, -1472621998, -1213800924, 1773167932, 2102277096, -1802443884, -334679055, 1379961774, -1910637922, -1143702981, -1054263953, -892157135, 1564253646, 859726899, -518536783, 1854272668, -1286296560, -2095128845, -135570606, 562671675, -1805979745, -321963249, 1206820173, 2071093549, -578927012, -821434741, 832213556, 944949014, -1036853670, -1737309279, -325850893, -281090533, -1619981574, -2098602482, 1218338375, -1687985655, 140586160, -1126585633, 1879855112, -725188139, 235177960, -2114113661, -1037524082, 2107456966, -1643937632, -96215481, 814541148, 1190016943, -1283505912, -1898040004, -2015870394, 593305643, 1316580704, -1804888975, 587719667, 1078518786, -2020355993, 1549930832, -128797951, -1457388059, 2431610, -520119142, 423507399, 663717940, 111418790, 1298828051, 151880348, -1358573163, -1420640521, -378115403, 18120744, 1428846807, 859448156, 2028214439, -352698383, 936052367, 763568371, 587371271, 1245125300, -1099665727, 804863777, 2141823978, 1290360955, -858157813, 1777171599, 1401288304, 411575556, 1562677469, 649207900, -553183317, -2080518077, -870393609, 110170740, -1326351183, 1831659737, -444958418, 520557946, -686455303, -1365377045, -347147852, -1461289001, 466809464, -724852406, 711546174, 945907658, -525386897, -675210154, -699082657, -1807720871, 1429392067, -1716082533, -746996239, -1536249199, -1215169084, 953427469, 113212163, -1439756510, 1122470361, -861585152, -231611466, -667279842, 1724977023, -543403231, -1483318259, 955359205, 1754524134, 1642700490, -1166821014, -1236253966, -1114482044, -1246225112, -2110117306, -21890603, 1738575336, 1288104326, 1703438971, 204595818, 799931748, 944338713, -690089038, 2121381501, 1670083743, 387046253, 1369609402, -1255998970, -989236763, -2003602168, 251481216, -556920070, 386494355, -236361315, -1257090156, 1846291629, -126920285, -337981972, -572235844, -448240245, 534572921, -38451214, 1096212046, -2106402349, 1990839026, 946773741, 1061705364, 638536189, 842940571, -310156068, 2083788906, 118578043, 640261693, 350024392, -1924040779, -606440848, 1089859028, -1639940971, -1199959329, 223014918, 1853879913, -1071236297, 1488438771, -1872150130, 252440641, -712230599, 1032414107, 667080344, -1009423420, -2008596494, 1383878104, 622306596, 1823667160, 690784465, -1849984903, 1875428436, 300069768, -2126385839, 788496700, -1399185011, -633636177, 1443373117, 1592767467, 1298645200, 99203081, -661224802, 9710971, -541819999, 1760926907, 389998617, 1013383503, 656258233, 1150478575, -1272077066, -1699999738, 209326722, -1426902937, -281627162, 809893713, -34541980, -942714400, -1244489602, -648033909, -866868930, 1721760243, 1118494146, 127466255, -549205972, -962284644, -1063811655, 241470215, -498499987, 1928536928, -469337799, 1626128542, -816237029, -1956051958, 1710103031, 1335728072, 290611758, -2044873378, -644087833, -1051508221, 256084592, 1746078579, -1537641010, -1929835922, 1782549543, -2076832813, 1990260507, -1467769774, 440073885, 1399659189, 646802426, -941227898, 1577914040, 1962986985, 1665075833, -862253895, 1382061617, 977968991, 1151139702, 140882694, -308121159, 1944026021, 72035864, -615225291, 1331801766, -766580955, 1281541624, 651864195, 1865693649, 263709209, -1040714649, 1745756583, -1138196166, -1925631192, -1861631783, 1071052817, -2010189954, 363100010, 91765705, 1302097236, -345267139, -840074611, -1679552269, 735386833, -593441568, -981176841, -421369679, -552546540, 176838035, 1239881156, -1733152070, -701577116, -767676212, 1464129169, -94959133, 2062328163, -8320175, -1343543551, 1363920604, 1645866911, -235376771, 1361694267, -82630422, 206050926, 2120886383, 1838200276, -1518319425, 192140742, 1492544378, -188107469, -1453946895, 944449987, -1705939834, -9389392, -1525067153, 768174965, -1551655428, 350683736, 446805591, 914877868, 1015634508, 815023833, -452167909, -1896140709, 1852599402, -217973639, 1713862406, 1638547074, -1708878201, 822001927, -152939528, 183888862, -843962124, -1314841098, -1912977403, 1046849918, 2071295605, -314410193, 849101278, 1962557389, 311930409, 440390127, -605610059, 1545816226, 1210789307, 141603180, 2026928750, -1483758624, -1170934196, -460340359, -834649550, -2009865396, -1105762925, 1842807811, 1094540235, 747735226, 1043645723, 1632549818, -105321513, -390889132, 1575250598, -1622687652, -1280020287, 1350091693, -1782302385, -1936207058, -1415956221, 290980015, -528620002, 2041784792, -278524932, -2084073679, -933140397, 1620121177, 1361753121, -1573991276, 727288241, -1107421837, 261972290, -2099668623, 697219789, -774738762, 2029762427, 1574622403, 2132929532, -190880700, 163045774, -1168113646, -421198170, -2039993072, 1494313891, -2011155352, -436348420, -770704972, -1681829763, -375003829, 1370750111, 254339183, 1874556784, 1964503175, -105594109, -1712338999, -1485469500, -1287950825, 1790361901, -166323114, 527522801, 1288941842, 502423088, -373606908, -1077906175, -241038582, -638700909, -276624748, 170216041, 433813101, 1937360622, -1010694203, -1544884878, 489121797, 2117260522, 1079137304, 619109322, -2061066370, 46388682, -1439203120, -1920562425, -2118293702, -820592666, 870723938, 1009207650, -550269052, -1486315046, -2011760225, 1377753470, 979900037, -1179969863, -967162433, 1686541432, 1263543484, 2053085551, -1992647899, -1239261042, 1197629569, 559008057, -1083944202, -521886492, -1662960059, -443735876, -1647129798, 799479799, -515425379, -326910045, 1050522380, 1549766503, -371859200, -1395916403, 638259024, -2118074422, -967665488, -878086641, -800047980, -208112323, -648894746, -451012208, -825559039, 448325109, -739325277, -1504835098, -1810545779, 54224195, -925979230, -425154958, 626740522, 83488475, -2058658419, -1152868778, -1775307907, -319792902, 814714331, 1009345151, 943860861, -2100575414, -112837948, -747487254, 1347656901, 75678014, -1138363232, -1307573599, 116741784, -705873565, -1290976961, -929032140, -417371845, 818214868, 1714602893, -972414480, -431858771, 1754476139, 1242930819, -1990209290, -726189155, -2076006639, 764082923, 962558087, -931805493, -223655856, -848399654, -2026245028, -380923021, 1585300933, -684885552, 1375891422, -317316257, -1559893963, -1857391665, 62596221, -854218471, -1275401794, 1545037587, 1347857883, 191502695, -1242392620, -1037208391, -1858947458, 1774201417, 1890823132, -2081745202, -1729828997, -1323568003, -329081410, 1553828341, -380829998, -84150341, -1567836505, 1458891810, -2035436280, 696449435, 419944731, 588628009, 1005591517, 1308749415, 505738346, 1615760968, -187046880, -1932096828, -1426039779, 1600982852, -1937968267, -1567936982, 1891673099, -1153212347, 279108676, 1821784894, 1428978136, -1864057257, 1157739612, 1206298805, 1516470388, 44647883, 1980694239, -424507489, -1942148778, -984385435, 1607184484, -1350100984, -1265090778, -1636330914, -813936732, 1089355850, -1890950384, 1065812931, 1444726045, -228884200, -584868285, 1391984351, -628132580, -1314471112, 769317914, 462235147, 639312252, 1946954247, -245984099, 2031148214, -212547304, -766944867, -1045300866, 401861876, -591947783, 1873036450, -156989187, 1635162134, 640367619, -1781404971, -849404453, -538000046, -1336934954, -1982733121, -1398987415, -719854621, 1784341623, 721782439, -1620215683, -1385071141, -1673258025, 1310115439, -2099128906, -1654207819, -1005282881, -2119831961, 1921423446, 1718364200, -1401555684, -624077338, -1932713820, -1201099528, -1029390961, -1934804368, 1695756006, 732956888, -2015755102, -356205645, -1645247782, -240053342, 1590894527, -153495720, -516610372, 937208760, 139713845, 1286688186, 1013247225, 2025316312, 196824121, -961768579, 1730161940, -1192542845, -1249512715, 1838076607, 279974577, 349072636, 916140850, -234762618, -1937978716, 1342078232, 437272999, 725125252, -1786476389, 1287530645, -2110343298, -1017117141, -1563996308, -49762230, 288949090, 2016505675, 2043643941, -335418768, -714459842, 659009547, -300222068, -854392789, -1576657759, 147383671, -1845886620, -517656320, 1924047700, -1166480321, -316330471, 1688878166, 745986886, -1318325499, -1389187156, 1150169079, 154095652, 1664417850, -1528504207, 979625834, 406919408, 1817989475, 1363764381, -391577216, 117405031, 1208036320, 28081650, 1208497882, 2140507286, 232551522, -1714778900, 152206423, -295649836, 3420};
    Assert.assertEquals(encryptedIntArray, XXTEA.stringToIntArray(encryptedString, true));
    String result = XXTEA.decrypt(encryptedString, key);
    Assert.assertEquals(string, result);
  }



}
