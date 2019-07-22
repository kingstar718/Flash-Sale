package top.wujinxing.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import top.wujinxing.entity.User;

/**
 * @author wujinxing
 * date 2019 2019/7/21 14:54
 * description
 */
@Component    //加盖注解，Service注入时不会有红线
//applicationContext里面已经声明了 dao.class的对象bean，
// 又扫描dao类的上面注解@Component声明为bean，
// 重复导致spring容器不能判断Service的成员自动装配哪个对象。
@Mapper
public interface UserDao {

    @Select("select * from sk_user where id = #{id}")
    public User getById(@Param("id") Long id);

    @Insert("insert into sk_user(id, name) values(#{id}, #{name})")
    public int insert(User user);
}
