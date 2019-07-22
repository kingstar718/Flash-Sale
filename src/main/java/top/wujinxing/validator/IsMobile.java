package top.wujinxing.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author wujinxing
 * date 2019 2019/7/22 16:13
 * description 自定义手机格式校验注解
 */
@Target({ElementType.METHOD, ElementType.FIELD,
            ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR,
            ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsMobileValidator.class}
)
public @interface IsMobile {

    boolean required() default true;//默认不能为空

    String message() default "收集号码格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};
}
