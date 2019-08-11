package top.wujinxing.rabbitmq;

import top.wujinxing.entity.User;

/**
 * @author wujinxing
 * date 2019 2019/8/10 15:13
 * description 秒杀的入队信息对象
 */
public class SeckillMessage {

    private User user;
    private long goodsId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
