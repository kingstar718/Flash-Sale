package top.wujinxing.redis;

/**
 * @author wujinxing
 * date 2019 2019/7/22 9:03
 * description 缓冲key前缀
 */
public interface KeyPrefix {

    /**
     * 有效期
     * @return int
     */
    public int expireSeconds();

    /**
     * 前缀
     * @return String
     */
    public String getPrefix();
}
