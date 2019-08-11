package top.wujinxing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.wujinxing.dao.GoodsDao;
import top.wujinxing.entity.FlashSaleGoods;
import top.wujinxing.entity.Goods;
import top.wujinxing.vo.GoodsVo;

import java.util.List;

/**
 * @author wujinxing
 * date 2019 2019/7/23 16:19
 * description 商品服务类
 */
@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    //减少库存的方法
    public boolean reduceStock(GoodsVo goods){
        FlashSaleGoods g = new FlashSaleGoods();
        g.setGoodsId(goods.getId());
        int ret = goodsDao.reduceStock(g);
        return ret > 0;
    }
}
