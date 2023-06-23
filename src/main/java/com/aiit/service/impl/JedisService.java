package com.aiit.service.impl;

import com.aiit.service.IJedisService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class JedisService implements IJedisService {

    private static Jedis jedis;

    public JedisService() {
        JedisPool pool = new JedisPool();
        jedis = pool.getResource();
    }

    @Override
    public void setToken(String token, String username) {
        jedis.set(token, username);
        jedis.expire(token, 360000);
    }

    @Override
    public boolean isToken(String token) {
        return jedis.get(token) != null;
    }

    @Override
    public String getUserId(String token) {
        if (token == null) {
            return null;
        }
        return jedis.get(token);
    }

}
