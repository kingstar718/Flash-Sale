package top.wujinxing.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.wujinxing.entity.FlashSaleGoods;
import top.wujinxing.entity.Goods;
import top.wujinxing.vo.GoodsVo;

import java.util.List;

/**
 * @author wujinxing
 * date 2019 2019/7/23 16:20
 * description 商品Dao
 */
@Mapper
public interface GoodsDao {

    @Select("select g.*, sg.stock_count, sg.start_date, sg.end_date, sg.seckill_price from sk_goods_seckill sg left join sk_goods g on sg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();

    @Select("select g.*, sg.stock_count, sg.start_date, sg.end_date, sg.seckill_price from sk_goods_seckill sg left join sk_goods g on sg.goods_id = g.id where g.id = #{goodsId}")
    public GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    @Update("update sk_goods_seckill set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    public int reduceStock(FlashSaleGoods g);
}
