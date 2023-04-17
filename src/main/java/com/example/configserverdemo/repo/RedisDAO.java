package com.example.configserverdemo.repo;

import com.example.configserverdemo.service.KeyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.json.Path;
import redis.clients.jedis.json.Path2;

@Repository
public class RedisDAO {
   /* @Autowired
    private RedisTemplate<String, Object> redisTemplate;*/

    @Autowired
    private UnifiedJedis client;
    @Autowired
    private KeyService service;
    public String saveConfig(String jsonObject) {
        /*String uniqueKey = service.getUniqueKey();
        redisTemplate.opsForHash().put(uniqueKey, uniqueKey, jsonObject);
        return uniqueKey;*/
        String uniqueKey = service.getUniqueKey();
        client.jsonSet(uniqueKey, jsonObject);
        return uniqueKey;
    }

    public String getConfig(String key) throws JsonProcessingException {
        client.jsonSet(key, Path.of("data.xyz"), "sumit");
        Object o = client.jsonGet(key);
        return new ObjectMapper().writeValueAsString(o);
    }

}
