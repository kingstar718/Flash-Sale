package top.wujinxing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wujinxing.entity.User;
import top.wujinxing.redis.UserKey;
import top.wujinxing.result.Result;
import top.wujinxing.redis.RedisService;
import top.wujinxing.service.UserService;

/**
 * @author wujinxing
 * date 2019 2019/7/21 14:18
 * description
 */
@Controller
@RequestMapping("/demo")
public class SimpleController {

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name", "good boy 你好呀");
        return "hello";
    }

    @GetMapping("/hello")
    @ResponseBody
    public Result<String> hello(){
        return Result.success("hello world");
    }

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    @ResponseBody
    public Result<User> deGet(){
        User user = userService.getById(3);
        //User user = userService.getById(id);
        return Result.success(user);
    }
    @GetMapping("/user2")
    @ResponseBody
    public Result<User> getById(Integer id){
        return Result.success(userService.getById(id));
    }

    /**
     * 事务测试
     * @return true
     */
    @GetMapping("/userTx")
    @ResponseBody
    public Result<Boolean> userTx(){
        userService.tx();
        return Result.success(true);
        //SQLIntegrityConstraintViolationException
    }

    @Autowired
    RedisService redisService;

    /*@GetMapping("/redisSet")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user = new User();
        user.setId(1);
        user.setName("Jsesp");
        //参数含义分别是 key前缀对象、key、对象
        //UserKey:id1
        Boolean b1 = redisService.set(UserKey.getById, ""+1, user);
        return Result.success(b1);
    }*/

    @GetMapping("/redisGet")
    @ResponseBody
    public Result<User> redisGet(){
        //参数的含义是 key前缀对象、key、类class  即什么类返回什么类对象
        User user = redisService.get(UserKey.getById, ""+1, User.class);
        return Result.success(user);
    }
}
