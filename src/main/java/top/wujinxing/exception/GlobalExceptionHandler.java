package top.wujinxing.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wujinxing.result.CodeMsg;
import top.wujinxing.result.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wujinxing
 * date 2019 2019/7/22 20:09
 * description 自定义全局异常拦截器
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();
        if (e instanceof GlobalException){
            GlobalException ex = (GlobalException) e;
            return Result.error(ex.getCodeMsg());
        }else if(e instanceof BindException){
            BindException bx = (BindException) e;
            List<ObjectError> errors = bx.getAllErrors();//绑定错误返回很多错误，是一个错误列表，只需要第一个错误
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));//给状态码填充参数
        }else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
