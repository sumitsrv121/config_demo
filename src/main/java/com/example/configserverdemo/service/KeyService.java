package com.example.configserverdemo.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KeyService {
    private static final String CONFIG_KEY_PREFIX = "config";
    private static final String CONFIG_KEY_SEPARATOR = "::";
    public String getUniqueKey() {
        return CONFIG_KEY_PREFIX + CONFIG_KEY_SEPARATOR + UUID.randomUUID();
    }
}
