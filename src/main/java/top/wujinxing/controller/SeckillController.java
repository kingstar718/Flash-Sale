package top.wujinxing.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.wujinxing.entity.FlashSaleOrder;
import top.wujinxing.entity.Goods;
import top.wujinxing.entity.OrderInfo;
import top.wujinxing.entity.User;
import top.wujinxing.rabbitmq.MQSender;
import top.wujinxing.rabbitmq.SeckillMessage;
import top.wujinxing.redis.GoodsKey;
import top.wujinxing.redis.RedisService;
import top.wujinxing.result.CodeMsg;
import top.wujinxing.result.Result;
import top.wujinxing.service.FlashSaleService;
import top.wujinxing.service.GoodsService;
import top.wujinxing.service.OrderService;
import top.wujinxing.vo.GoodsVo;

import java.util.HashMap;
import java.util.List;

/**
 * @author wujinxing
 * date 2019 2019/7/24 9:48
 * description
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    FlashSaleService flashSaleService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender mqSender;

    //做标记，判断商品是否被处理过了
    private HashMap<Long,Boolean> localOverMap = new HashMap<>();

    @PostMapping("/do_seckill")
    @ResponseBody
    public Result<Integer> doSeckill(Model model,
                            User user,
                            @RequestParam("goodsId")long goodsId){
        model.addAttribute("user", user);
        //if (user==null) return "login";

        if (user==null) return Result.error(CodeMsg.SESSION_ERROR);

        //内存标记，减少redis访问
        boolean over =localOverMap.get(goodsId);
        if (over) return Result.error(CodeMsg.SECKILL_OVER);

        //使用redis
        //预减库存
        long stock = redisService.derc(GoodsKey.getGoodsStock, ""+goodsId);
        if (stock<0){
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.REPEATE_SECKILL); //库存不足，秒杀失败
        }

        //秒杀信息入队
        SeckillMessage message = new SeckillMessage();
        message.setUser(user);
        message.setGoodsId(goodsId);
        mqSender.sendSeckillMessage(message);
        return Result.success(0); //表示排队中
        /*//判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getGoodsStock();
        if (stock <= 0){
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        //判断是否已经秒杀到了
        FlashSaleOrder order = orderService.getFlashSaleOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order!=null){
            return Result.error(CodeMsg.REPEATE_SECKILL);
        }
        //减库存  下订单  写入秒杀订单(事务操作)
        OrderInfo orderInfo = flashSaleService.flashSale(user, goods);
        //model.addAttribute("orderInfo", orderInfo);
        //model.addAttribute("goods", goods);
        //return "order_detail";
        return Result.success(orderInfo);*/
    }

    /**
     * 系统初始化，将商品信息加载到redis和本地内存
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        if (goodsVoList == null) return;
        for (GoodsVo goods: goodsVoList){
            redisService.set(GoodsKey.getGoodsStock, ""+goods.getId(), goods.getStockCount());
            //初始化商品都是没有经过处理的

            //内存标记 ,表示初始化时商品都是没有处理过的
            localOverMap.put(goods.getId(), false);
        }
    }


    /**
     *orderId 秒杀成功
     *-1 秒杀失败
     * 0 排队中
     */
    @GetMapping("/result")
    @ResponseBody
    public Result<Long> result(Model model,
                               User user,
                               @RequestParam("goodsId")long goodsId){

        model.addAttribute("user", user);
        if (user == null) return Result.error(CodeMsg.SESSION_ERROR);

        long orderId = flashSaleService.getSeckillResult(user.getId(), goodsId);
        return Result.success(orderId);
    }
}
