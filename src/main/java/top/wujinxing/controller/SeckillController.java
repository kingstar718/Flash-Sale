package top.wujinxing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.wujinxing.entity.FlashSaleOrder;
import top.wujinxing.entity.Goods;
import top.wujinxing.entity.OrderInfo;
import top.wujinxing.entity.User;
import top.wujinxing.result.CodeMsg;
import top.wujinxing.result.Result;
import top.wujinxing.service.FlashSaleService;
import top.wujinxing.service.GoodsService;
import top.wujinxing.service.OrderService;
import top.wujinxing.vo.GoodsVo;

/**
 * @author wujinxing
 * date 2019 2019/7/24 9:48
 * description
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    FlashSaleService flashSaleService;

    @PostMapping("/do_seckill")
    @ResponseBody
    public Result<OrderInfo> doSeckill(Model model,
                            User user,
                            @RequestParam("goodsId")long goodsId){
        model.addAttribute("user", user);
        //if (user==null) return "login";

        if (user==null) return Result.error(CodeMsg.SESSION_ERROR);

        //判断库存
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
        return Result.success(orderInfo);
    }
}
