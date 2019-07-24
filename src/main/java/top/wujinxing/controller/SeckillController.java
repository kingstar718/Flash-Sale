package top.wujinxing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.wujinxing.entity.FlashSaleOrder;
import top.wujinxing.entity.Goods;
import top.wujinxing.entity.OrderInfo;
import top.wujinxing.entity.User;
import top.wujinxing.result.CodeMsg;
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
    public String doSeckill(Model model, User user, @RequestParam("goodsId")long goodsId){
        model.addAttribute("user", user);
        if (user==null) return "login";

        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getGoodsStock();
        if (stock <= 0){
            model.addAttribute("errmsg", CodeMsg.SECKILL_OVER.getMsg());
            return "seckill_fail";
        }
        //判断是否已经秒杀到了
        FlashSaleOrder order = orderService.getFlashSaleOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order!=null){
            model.addAttribute("errmsg", CodeMsg.REPEATE_SECKILL.getMsg());
        }
        //减库存  下订单  写入秒杀订单(事务操作)
        OrderInfo orderInfo = flashSaleService.flashSale(user, goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);
        return "order_detail";
    }
}
