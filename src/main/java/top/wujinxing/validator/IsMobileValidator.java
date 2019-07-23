package top.wujinxing.validator;

import org.apache.commons.lang3.StringUtils;
import top.wujinxing.util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author wujinxing
 * date 2019 2019/7/22 16:16
 * description 自定义手机格式校验注解
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    //初始化
    @Override
    public void initialize(IsMobile isMobile) {
        required = isMobile.required();
    }

    //判断方法
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("进入isMobile的isValid方法");
        if (required){
            return ValidatorUtil.isMobile(value);
        }else {
            if (StringUtils.isEmpty(value)){
                return true;
            }else {
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}
