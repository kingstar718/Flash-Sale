package top.wujinxing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wujinxing.entity.User;
import top.wujinxing.redis.RedisService;
import top.wujinxing.result.Result;
import top.wujinxing.service.UserService;

/**
 * @author wujinxing
 * date 2019 2019/8/3 13:58
 * description user测试类
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/info")
    @ResponseBody
    public Result<User> info(Model model, User user){
        return Result.success(user);
    }
}
