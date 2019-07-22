package top.wujinxing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wujinxing.result.CodeMsg;
import top.wujinxing.result.Result;
import top.wujinxing.service.UserService;
import top.wujinxing.util.ValidatorUtil;
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

    @RequestMapping("/to_hello")
    public String toHello(){
        return "hello";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<CodeMsg> doLogin(LoginVo loginVo){
        LOGGER.info(loginVo.toString());
        //设置@isMobile注解之后，可以不需要下面的注解了
        /*String passInput = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        if (StringUtils.isEmpty(passInput)){
            LOGGER.info("密码为空");
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if (!ValidatorUtil.isMobile(mobile)){
            LOGGER.info("手机号为空或错误");
            return Result.error(CodeMsg.MOBILE_ERROR);
        }*/
        //登录
        CodeMsg cm = userService.login(loginVo);
        if (cm.getCode()==0){
            return Result.success(CodeMsg.SUCCESS);
        }else {
            return Result.error(cm);
        }
    }


}
