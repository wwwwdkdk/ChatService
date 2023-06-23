package com.aiit.service;

public interface IJedisService {

    //向redis保存token
    void setToken(String token, String username);

    //通过redis验证token是否正确
    boolean isToken(String token);

    //通过token拿到用户id
    String getUserId(String token);
}
