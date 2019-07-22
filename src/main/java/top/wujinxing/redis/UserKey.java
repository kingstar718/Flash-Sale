package top.wujinxing.redis;

/**
 * @author wujinxing
 * date 2019 2019/7/22 9:38
 * description User的前缀key对象
 */
public class UserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600*24*2; //两天

    //防止被外面实例化
    private UserKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }

    //需要缓冲的字段
    public static UserKey token = new UserKey(TOKEN_EXPIRE, "token");
    public static UserKey getById = new UserKey(0, "id");
}
