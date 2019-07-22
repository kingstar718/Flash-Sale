package top.wujinxing.redis;

/**
 * @author wujinxing
 * date 2019 2019/7/22 9:15
 * description 基本的key前缀
 */
public abstract class BasePrefix implements KeyPrefix {
    private int expireSeconds;
    private String prefix;

    //默认0代表永不过期
    public BasePrefix(String prefix){
        this(0,prefix);
    }

    public BasePrefix(int expireSeconds, String prefix){
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        //拿到参数类类名
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
