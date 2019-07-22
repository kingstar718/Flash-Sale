package top.wujinxing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wujinxing.dao.UserDao;
import top.wujinxing.entity.User;

/**
 * @author wujinxing
 * date 2019 2019/7/21 14:55
 * description
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getById(Integer id){
        return userDao.getById(id);
    }

    @Transactional //事务测试
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
    }
}
