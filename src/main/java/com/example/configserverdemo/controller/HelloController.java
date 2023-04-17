package com.example.configserverdemo.controller;

import com.example.configserverdemo.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HelloController {
    @Autowired
    private RedisService redisService;
    @PostMapping("/")
    public String greet(@RequestBody Map<String, Object> data) throws JsonProcessingException {
        System.out.println(data);
        String jsonData = new ObjectMapper().writeValueAsString(data);
        System.out.println(jsonData);
        return redisService.saveConfig(jsonData);
        //data.forEach((key, value) -> System.out.println("Key :"+ key+" ->  Value: "+value));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getConfig(@PathVariable("id") String key) {
        try {
            return redisService.getConfig(key);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
