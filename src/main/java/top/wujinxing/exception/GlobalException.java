package top.wujinxing.exception;

import top.wujinxing.result.CodeMsg;

/**
 * @author wujinxing
 * date 2019 2019/7/22 20:16
 * description 自定义全局异常类
 */
public class GlobalException extends RuntimeException{

    private static final long serivalVersionID =1L;

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg){
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg(){
        return codeMsg;
    }
}
