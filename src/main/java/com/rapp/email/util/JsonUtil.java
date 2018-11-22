package com.rapp.email.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

import dk.nykredit.jackson.dataformat.hal.HALMapper;

public final class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static HALMapper objectMapper = new HALMapper();

    public static String toJson(Object object) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Convert object to json string failure", e);
        }

        return json;
    }
}
