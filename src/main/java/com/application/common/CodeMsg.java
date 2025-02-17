package com.application.common;

public class CodeMsg {
    private int code;
    private String message;
    // 按照模块定义CodeMsg
    // 通用异常
    public static CodeMsg SUCCESS = new CodeMsg(200,"success");

    public static CodeMsg Failed_Unauthorized = new CodeMsg(401,"please login first");
    public static CodeMsg SERVER_EXCEPTION = new CodeMsg(500100,"服务端异常");
    public static CodeMsg PARAMETER_ISNULL = new CodeMsg(500101,"输入参数为空");
    // 业务异常
    public static CodeMsg USER_NOT_EXSIST = new CodeMsg(500102,"用户不存在");
    public static CodeMsg USER_HAVE_EXSIST = new CodeMsg(500102,"用户名称已存在");
    public static CodeMsg ONLINE_USER_OVER = new CodeMsg(500103,"在线用户数超出允许登录的最大用户限制。");
    public static CodeMsg SESSION_NOT_EXSIST =  new CodeMsg(500104,"不存在离线session数据");
    public static CodeMsg NOT_FIND_DATA = new CodeMsg(500105,"查找不到对应数据");
    public static CodeMsg USER_OR_PASS_ERROR = new CodeMsg(500102,"账号或者密码错误，请重试！");


    private CodeMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public CodeMsg(String message) {
        this.code = 400; // 普通异常
        this.message = message;
    }
    public int getcode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
