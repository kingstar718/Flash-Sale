package top.wujinxing.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wujinxing.dao.UserDao;
import top.wujinxing.entity.User;
import top.wujinxing.exception.GlobalException;
import top.wujinxing.redis.RedisService;
import top.wujinxing.redis.UserKey;
import top.wujinxing.result.CodeMsg;
import top.wujinxing.util.MD5Util;
import top.wujinxing.util.UUIDUtil;
import top.wujinxing.vo.LoginVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wujinxing
 * date 2019 2019/7/21 14:55
 * description
 */
@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    private UserDao userDao;

    @Autowired
    RedisService redisService;//将私人信息存到第三方缓存中

    public User getById(Long id){
        //对象缓存
        User user = redisService.get(UserKey.getById, ""+id, User.class);
        if (user!=null) return user;
        //取数据库
        user = userDao.getById(id);
        //再存入缓存
        if (user!=null) redisService.set(UserKey.getById, ""+id, user);

        return user;
    }

    public boolean updatePassword(String token, long id, String fromPass){
        //取user
        User user = getById(id);
        if (user==null) throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);

        //更新数据库
        User toBeUpdate = new User();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(fromPass, user.getSalt()));
        userDao.update(toBeUpdate);
        //更新缓存，先删除在插入
        redisService.delete(UserKey.getById, ""+id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(UserKey.token, token, user);
        return true;
    }

    /**
     * 根据token获取用户信息
     */
    public User getByToken(HttpServletResponse response,String token) {
        //先做参数校验
        if (StringUtils.isEmpty(token)){
            return null;
        }
        User user =  redisService.get(UserKey.token, token, User.class);
        //延长缓存的有效期   有效期实际上应该是最后一次操作+有效时间
        if (user != null){
            addCookie(response, token, user);
        }
        return user;

    }

    /*@Transactional //事务测试
    //不加@Tr， 尽管仍会报错，但u2会插入到数据库中，加@，则不会
    public boolean tx(){
        User u2 = new User();
        u2.setId(5);
        u2.setName("lilsssi");
        userDao.insert(u2);

        User u1 = new User();
        u1.setId(1);
        u1.setName("lili");
        userDao.insert(u1);

        return true;
    }*/
    public String login(HttpServletResponse response, LoginVo loginVo){
        if (loginVo==null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        User user = getById(Long.parseLong(mobile));//判断手机号是否存在
        if (user==null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, dbSalt);
        if (!calcPass.equals(dbPass)){ //将表单密码加密后和数据库的进行对比
            LOGGER.info("密码匹配失败");
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        LOGGER.info("密码匹配成功");
        //生成Cookie
        String token = UUIDUtil.uuid();
        LOGGER.info("生成的token值为："+token);
        addCookie(response, token, user); //独立方法抽离
        return token;
    }

    //添加addCookie的方法
    private void addCookie(HttpServletResponse response, String token, User user){
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
