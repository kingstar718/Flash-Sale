package top.wujinxing.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.wujinxing.entity.User;
import top.wujinxing.service.UserService;

import javax.servlet.http.HttpServletResponse;

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

    @GetMapping("/to_list")
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
}
