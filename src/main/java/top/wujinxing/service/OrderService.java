package top.wujinxing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wujinxing.dao.OrderDao;
import top.wujinxing.entity.FlashSaleOrder;
import top.wujinxing.entity.Goods;
import top.wujinxing.entity.OrderInfo;
import top.wujinxing.entity.User;
import top.wujinxing.vo.GoodsVo;

import java.util.Date;

/**
 * @author wujinxing
 * date 2019 2019/7/24 10:00
 * description 订单
 */
@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    public FlashSaleOrder getFlashSaleOrderByUserIdGoodsId(Long userId, long goodsId) {
        return orderDao.getFlashSaleOrderByUserIdGoodsId(userId, goodsId);
    }

    public OrderInfo getOrderBuId(long orderId){
        return orderDao.getOrderById(orderId);
    }


    //因为要同时分别在订单详情表和秒杀订单表都新增一条数据，所以要保证两个操作是一个事物
    //生成订单
    @Transactional
    public OrderInfo createOrder(User user, GoodsVo goodsVo){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getGoodsPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId = orderDao.insert(orderInfo);

        FlashSaleOrder flashSaleOrder = new FlashSaleOrder();
        flashSaleOrder.setGoodsId(goodsVo.getId());
        flashSaleOrder.setOrderId(orderInfo.getId());
        flashSaleOrder.setUserId(user.getId());
        orderDao.insertFlashSaleOrder(flashSaleOrder);

        return orderInfo;
    }
}
