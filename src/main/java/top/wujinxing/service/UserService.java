package top.wujinxing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wujinxing.dao.UserDao;
import top.wujinxing.entity.User;
import top.wujinxing.result.CodeMsg;
import top.wujinxing.util.MD5Util;
import top.wujinxing.vo.LoginVo;

/**
 * @author wujinxing
 * date 2019 2019/7/21 14:55
 * description
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getById(Long id){
        return userDao.getById(id);
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
    public CodeMsg login(LoginVo loginVo){
        if (loginVo==null) return CodeMsg.SERVER_ERROR;
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        User user = getById(Long.parseLong(mobile));
        if (user==null) return CodeMsg.MOBILE_NOT_EXIST;
        //验证密码
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, dbSalt);
        if (!calcPass.equals(dbPass)){ //将表单密码加密后和数据库的进行对比
            return CodeMsg.PASSWORD_ERROR;
        }
        return CodeMsg.SUCCESS;

    }
}
