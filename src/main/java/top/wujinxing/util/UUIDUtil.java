package top.wujinxing.util;

import java.util.UUID;

/**
 * @author wujinxing
 * date 2019 2019/7/23 9:42
 * description 唯一ID生成类
 */
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static void main(String[] args) {
        System.out.println(UUIDUtil.uuid());
    }
}
