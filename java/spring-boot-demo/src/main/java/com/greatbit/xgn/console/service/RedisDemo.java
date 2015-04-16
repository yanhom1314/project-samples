package com.greatbit.xgn.console.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RedisDemo {
    @Autowired
    private RedisTemplate<String, String> template;

    public String get(String keyId) {
        String msg = null;
        if (template != null) {
            msg = template.execute((RedisConnection redisConnection) -> {
                RedisSerializer<String> serializer = template.getStringSerializer();
                byte[] key = serializer.serialize(keyId);
                byte[] value = redisConnection.get(key);
                if (value == null) value = new byte[]{1, 2, 34};
                return new String(value);
            });
        }
        return msg;
    }

    public Map<String, String> hget(String keyId) {
        Map<String, String> rmap = null;
        if (template != null) {
            rmap = template.execute((RedisConnection redisConnection) -> {
                Map<String, String> map = new HashMap<>();
                RedisSerializer<String> serializer = template.getStringSerializer();
                byte[] key = serializer.serialize(keyId);
                redisConnection.hGetAll(key).forEach((kb, vb) -> {
                    map.put(serializer.deserialize(kb), serializer.deserialize(vb));
                });
                return map;
            });
        }

        return rmap;
    }
}