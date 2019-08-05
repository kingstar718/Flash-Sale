package top.wujinxing.vo;

import top.wujinxing.entity.User;

/**
 * @author wujinxing
 * date 2019 2019/8/5 14:15
 * description
 */
public class GoodsDetailVo {
    private int seckillStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goods ;
    private User user;

    public int getSeckillStatus() {
        return seckillStatus;
    }

    public void setSeckillStatus(int seckillStatus) {
        this.seckillStatus = seckillStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
