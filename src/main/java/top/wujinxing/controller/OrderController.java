package top.wujinxing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wujinxing.entity.Goods;
import top.wujinxing.entity.OrderInfo;
import top.wujinxing.entity.User;
import top.wujinxing.redis.RedisService;
import top.wujinxing.result.CodeMsg;
import top.wujinxing.result.Result;
import top.wujinxing.service.GoodsService;
import top.wujinxing.service.OrderService;
import top.wujinxing.service.UserService;
import top.wujinxing.vo.GoodsVo;
import top.wujinxing.vo.OrderDetailVo;

/**
 * @author wujinxing
 * date 2019 2019/8/5 21:23
 * description 获取订单
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @Autowired
    OrderService orderService;
    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model,
                                      User user,
                                      @RequestParam("orderId")long orderId){
        if (user == null) return Result.error(CodeMsg.SESSION_ERROR);

        OrderInfo order = orderService.getOrderBuId(orderId);

        if (order==null) return Result.error(CodeMsg.ORDER_NOT_EXIST);

        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        return Result.success(vo);
    }
}
