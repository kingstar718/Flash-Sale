package top.wujinxing.vo;

import top.wujinxing.entity.OrderInfo;

/**
 * @author wujinxing
 * date 2019 2019/8/5 21:28
 * description
 */
public class OrderDetailVo {
    private GoodsVo goods;
    private OrderInfo order;
    public GoodsVo getGoods() {
        return goods;
    }
    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }
    public OrderInfo getOrder() {
        return order;
    }
    public void setOrder(OrderInfo order) {
        this.order = order;
    }
}
