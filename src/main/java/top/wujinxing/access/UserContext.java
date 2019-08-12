package top.wujinxing.access;

import top.wujinxing.entity.User;

/**
 * @author wujinxing
 * date 2019 2019/8/12 23:32
 * description 数据会存入当前线程
 */
public class UserContext {

    private static ThreadLocal<User> userHolder = new ThreadLocal<User>();

    public static void setUser(User user) {
        userHolder.set(user);
    }

    public static User getUser() {
        return userHolder.get();
    }
}
