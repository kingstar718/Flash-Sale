package top.wujinxing.redis;

/**
 * @author wujinxing
 * date 2019 2019/8/10 16:36
 * description
 */
public class SeckillKey extends BasePrefix{

    private SeckillKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SeckillKey isGoodsOver = new SeckillKey(0,"go");
    public static SeckillKey getSeckillPath = new SeckillKey(60,"mp");
    public static SeckillKey getSeckillVerifyCode = new SeckillKey(300,"vc");
}