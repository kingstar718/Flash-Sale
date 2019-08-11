package top.wujinxing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wujinxing.entity.FlashSaleOrder;
import top.wujinxing.entity.Goods;
import top.wujinxing.entity.OrderInfo;
import top.wujinxing.entity.User;
import top.wujinxing.redis.RedisService;
import top.wujinxing.redis.SeckillKey;
import top.wujinxing.vo.GoodsVo;

/**
 * @author wujinxing
 * date 2019 2019/7/24 10:14
 * description
 */
@Service
public class FlashSaleService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo flashSale(User user, GoodsVo goods) {
        //减库存， 下订单， 写入秒杀订单
        boolean success = goodsService.reduceStock(goods);
        if (success){
            return orderService.createOrder(user, goods);
        }else {
            //新加
            setGoodsOver(goods.getId());
            return null;
        }
    }

    public long getSeckillResult(long userId, long goodsId){
        FlashSaleOrder order = orderService.getFlashSaleOrderByUserIdGoodsId(userId, goodsId);
        if (order != null){
            return order.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver){
                return -1;
            } else{
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId){
        redisService.set(SeckillKey.isGoodsOver, ""+goodsId, true);
    }

    private boolean getGoodsOver(long goodsId){
        return redisService.exists(SeckillKey.isGoodsOver, ""+goodsId);
    }
}
