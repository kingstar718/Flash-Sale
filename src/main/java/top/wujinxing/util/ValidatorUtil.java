package top.wujinxing.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wujinxing
 * date 2019 2019/7/22 16:20
 * description
 */
public class ValidatorUtil {
    //默认以1开头后面加10个数字为手机号
    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String src){
        if(StringUtils.isEmpty(src)){
            return false;
        }
        Matcher m = mobile_pattern.matcher(src);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(ValidatorUtil.isMobile("1818181818"));
        System.out.println(ValidatorUtil.isMobile("18181818181"));
    }
}
