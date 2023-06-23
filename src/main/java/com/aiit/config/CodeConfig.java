package com.aiit.config;

public class CodeConfig {
    public final static String SUCCESS = "10000";           // 成功
    public final static String FAILURE = "-10000";          //失败
    public final static String ERROR_TOKEN = "-10001";      //错误token
    public final static String NO_TOKEN = "-10002";         //缺少token
    public final static String NO_USER_ID = "-10003";       //缺少userId

    public static String getCode(String data) {
        return getCode(data != null);
    }

    public static String getCode(Boolean result) {
        return result ? CodeConfig.SUCCESS : CodeConfig.FAILURE;
    }

}
