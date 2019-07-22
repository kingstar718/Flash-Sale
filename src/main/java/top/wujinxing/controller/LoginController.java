package top.wujinxing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wujinxing.result.Result;
import top.wujinxing.service.UserService;
import top.wujinxing.vo.LoginVo;

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

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(LoginVo loginVo){
        LOGGER.info(loginVo.toString());
        return null;
    }


}
