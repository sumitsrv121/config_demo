package com.example.configserverdemo.service;

import com.example.configserverdemo.repo.RedisDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    private RedisDAO dao;
    public String saveConfig(String jsonData) {
        try {
           return dao.saveConfig(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getConfig(String key) throws JsonProcessingException {
        try {
            return dao.getConfig(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
