package top.wujinxing.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import top.wujinxing.entity.User;
import top.wujinxing.redis.GoodsKey;
import top.wujinxing.redis.RedisService;
import top.wujinxing.service.GoodsService;
import top.wujinxing.service.UserService;
import top.wujinxing.vo.GoodsVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author wujinxing
 * date 2019 2019/7/23 9:54
 * description
 */
@Controller
@RequestMapping("/goods")
public class GoodController {

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    //新的WebContext不再需要
    /*@Autowired
    ApplicationContext applicationContext;*/

    //@GetMapping("/to_list")
    public String toList(Model model,
                         User user
                         /*HttpServletResponse response,
                         @CookieValue(value = UserService.COOKIE_NAME_TOKEN,required = false)String cookieToken,
                         @RequestParam(value = UserService.COOKIE_NAME_TOKEN,required = false)String paramToken*/){
        /*if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
            return "login";
        }
        //此时的逻辑才是正确的，即假如paramToken为空，取cookieToken
        String token = StringUtils.isEmpty(paramToken)? cookieToken: paramToken;
        User user = userService.getByToken(response, token);*/
        model.addAttribute("user",user);
        System.out.println("user的值为"+user.toString());
        return "goods_list";
    }

    //JMeter压测数据   932/sec  1000线程10次循环
    //页面静态化后    2100/sec  1000线程10次循环
    @GetMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request,
                       HttpServletResponse response,
                       Model model,
                       User user){
        model.addAttribute("user",user);
        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) return html;
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);

        //process需要的参数
        //SpringWebContext弃用 新的为WebContext
        WebContext springWebContext = new WebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap());

        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", springWebContext);
        if (!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        //结果输出
        return html;
        //return "goods_list";
    }

    //跳转到详情页
    @GetMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String toDetail(HttpServletRequest request,
                           HttpServletResponse response,
                           Model model,
                           User user,
                           @PathVariable("goodsId")long goodsId){
        //互联网企业经常使用snowflake算法来生成ID，而不是使用UUID
        model.addAttribute("user",user);

        //改写第一步。取缓存
        String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
        if (!StringUtils.isEmpty(html)) return html;

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        //秒杀逻辑
        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long nowTime = System.currentTimeMillis();
        int flashSaleStatus = 0;  //状态字符
        int remainSeconds = 0;  //还剩余多少秒
        if (nowTime < startTime){//秒杀未开始,倒计时
            flashSaleStatus = 0;
            remainSeconds = (int)((startTime-nowTime)/1000); //转换，且为秒
        }else if (nowTime > endTime){//秒杀已结束
            flashSaleStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            flashSaleStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("flashSaleStatus", flashSaleStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        //改写第二步。 手动渲染
        WebContext springWebContext = new WebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", springWebContext);
        if (!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html);
        }
        //return "goods_detail";
        return html;
    }
}
