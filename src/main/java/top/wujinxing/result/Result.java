package top.wujinxing.result;

/**
 * @author wujinxing
 * date 2019 2019/7/21 15:00
 * description
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    /**
     * 成功的时候调用
     * @param data 数据
     * @param <T> 类型
     * @return 数据
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    /**
     * 失败的时候调用
     * @param codeMsg 错误类
     * @param <T> 类型
     * @return 错误类
     */
    public static  <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }

    private Result(T data){
        this.data = data;
    }

    private Result(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private Result(CodeMsg codeMsg){
        if (codeMsg!=null){
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
