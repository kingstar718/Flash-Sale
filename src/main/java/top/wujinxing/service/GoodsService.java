package top.wujinxing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.wujinxing.dao.GoodsDao;
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
}
