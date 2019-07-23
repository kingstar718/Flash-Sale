package top.wujinxing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wujinxing.result.CodeMsg;
import top.wujinxing.result.Result;
import top.wujinxing.service.UserService;
import top.wujinxing.util.ValidatorUtil;
import top.wujinxing.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

/**
 * @author wujinxing
 * date 2019 2019/7/22 11:48
 * description
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/to_hello")
    public String toHello(){
        return "hello";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, LoginVo loginVo){//加入JSR303参数校验
        LOGGER.info(loginVo.toString());
        /*CodeMsg cm = userService.login(response, loginVo);
        if (cm.getCode()==0){
            return Result.success(CodeMsg.SUCCESS);
        }else {
            return Result.error(cm);
        }*/
        String token = userService.login(response,loginVo);
        return Result.success(token);
    }
}
