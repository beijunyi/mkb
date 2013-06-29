package im.grusis.mkb.core.emulator;

import java.util.LinkedHashMap;
import java.util.Map;

import im.grusis.mkb.core.emulator.game.model.basic.Goods;
import im.grusis.mkb.core.emulator.game.model.basic.GoodsList;
import im.grusis.mkb.core.emulator.game.model.basic.UserInfo;
import im.grusis.mkb.core.emulator.game.model.response.ShopBuyResponse;
import im.grusis.mkb.core.emulator.game.model.response.ShopGetGoodsResponse;
import im.grusis.mkb.core.exception.ServerNotAvailableException;
import im.grusis.mkb.core.exception.UnknownErrorException;
import im.grusis.mkb.core.exception.WrongCredentialException;
import im.grusis.mkb.core.repository.model.MkbAccount;
import im.grusis.mkb.core.service.AccountService;
import im.grusis.mkb.core.service.AssetsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmulatorShop {

  private static final Logger LOG = LoggerFactory.getLogger(EmulatorShop.class);

  @Autowired AccountService accountService;
  @Autowired AssetsService assetsService;
  @Autowired EmulatorCore core;
  @Autowired EmulatorUser user;



  public GoodsList gameShopGetGoodsList(String username, boolean refresh) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    GoodsList goodsList;
    if(refresh || (goodsList = assetsService.getGoods()) == null) {
      ShopGetGoodsResponse response = core.gameDoAction(username, "shop.php", "GetGoods", null, ShopGetGoodsResponse.class);
      if(response.badRequest()) {
        throw new UnknownErrorException();
      }
      goodsList = response.getData();
      assetsService.saveAssets(goodsList);
    }
    return goodsList;
  }

  public Goods gameShopGetGoods(String username, int goodsId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    Goods goods = assetsService.findGoods(goodsId);
    if(goods == null) {
      gameShopGetGoodsList(username, true);
      goods = assetsService.findGoods(goodsId);
      if(goods == null) {
        throw new UnknownErrorException();
      }
    }
    return goods;
  }

  public String gamePurchase(String username, int goodsId) throws ServerNotAvailableException, UnknownErrorException, WrongCredentialException {
    UserInfo userInfo = user.gameGetUserInfo(username, false);
    Goods goods = gameShopGetGoods(username, goodsId);
    LOG.info("{} is purchasing {}", userInfo, goods);

    Map<String, String> paramMap = new LinkedHashMap<String, String>();
    paramMap.put("GoodsId", Integer.toString(goodsId));
    ShopBuyResponse response = core.gameDoAction(username, "shop.php", "Buy", paramMap, ShopBuyResponse.class);
    if(response.badRequest()) {
      if(response.noCurrency()) {
        LOG.error("{} cannot purchase {} due to insufficient currency. Require {}", userInfo, goods, goods.getCostDescription());
      } else {
        throw new UnknownErrorException();
      }
      return null;
    }
    LOG.info("{} has successfully purchased {} for {}", userInfo, goods, goods.getCostDescription());
    return response.getData();
  }

}
