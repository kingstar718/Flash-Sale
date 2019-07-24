package top.wujinxing.dao;

import org.apache.ibatis.annotations.*;
import top.wujinxing.entity.FlashSaleOrder;
import top.wujinxing.entity.OrderInfo;

/**
 * @author wujinxing
 * date 2019 2019/7/24 10:27
 * description 订单Dao
 */
@Mapper
public interface OrderDao {

    @Select("select * from sk_order where user_id = #{userId} and goods_id = #{goodsId}")
    FlashSaleOrder getFlashSaleOrderByUserIdGoodsId(@Param("userId") Long userId, @Param("goodsId") long goodsId);


    //通过@SelectKey使insert成功后返回主键id，也就是订单id
    @Insert("insert into sk_order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    public long insert(OrderInfo orderInfo);

    @Insert("insert into sk_order (user_id, goods_id, order_id) values (#{userId}, #{goodsId}, #{orderId})")
    public int insertFlashSaleOrder(FlashSaleOrder flashSaleOrder);


    @Select("select * from sk_order_info where id = #{orderId}")
    public OrderInfo getOrderById(@Param("orderId")long orderId);
}
