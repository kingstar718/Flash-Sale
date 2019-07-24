package top.wujinxing.entity;

import java.util.Date;

/**
 * @author wujinxing
 * date 2019 2019/7/23 16:08
 * description 秒杀商品实体类
 */
public class FlashSaleGoods {
    private Long id;  //秒杀商品ID
    private Long goodsId;   //商品ID
    private Integer stockCount; //库存数量
    private Date startDate; //秒杀开始时间
    private Date endDate;   //秒杀结束时间
    private int version;    //并发版本控制

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
