package com.application.common;

/**
 * @program:
 * @description:
 * @author:
 * @create: 2023-06-29 13:39
 **/
public class Result<T> {

    private String message;
    private int code;
    private T data;

    private Result(T data) {
        this.code = 200;
        this.message = "成功";
        this.data = data;
    }

    private Result(CodeMsg cm) {
        if(cm == null){
            return;
        }
        this.code = cm.getcode();
        this.message = cm.getMessage();
    }


    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }




    /**
     * 成功时候的调用
     * @return
     */
    public static <T> Result<T> success(Integer code,String message,T data){
        return new Result( code, message, data);
    }


    /**
     * 成功时候的调用
     * @return
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    /**
     * 成功，不需要传入参数
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> success(){
        return (Result<T>) success("");
    }
    /**
     * 失败时候的调用
     * @return
     */
    public static <T> Result<T> error(CodeMsg cm){
        return new Result<T>(cm);
    }
    /**
     * 失败时候的调用,扩展消息参数
     * @param cm
     * @param msg
     * @return
     */
    public static <T> Result<T> error(CodeMsg cm,String msg){
        cm.setMessage(cm.getMessage()+"--"+msg);
        return new Result<T>(cm);
    }
    public T getData() {
        return data;
    }
    public String getMessage() {
        return message;
    }
    public int getcode() {
        return code;
    }
}
