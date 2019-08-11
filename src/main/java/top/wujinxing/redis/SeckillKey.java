package top.wujinxing.redis;

/**
 * @author wujinxing
 * date 2019 2019/8/10 16:36
 * description
 */
public class SeckillKey extends BasePrefix{

    private SeckillKey(String prefix){
        super(prefix);
    }

    public static SeckillKey isGoodsOver =new SeckillKey("go");
}
