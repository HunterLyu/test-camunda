package com.prudential.epos.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.support.JacksonUtils;

public class JsonUtil {
    public static <T> String toJson(T bean) {
        try {
            return JacksonUtils.enhancedObjectMapper().writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String str, Class clazz) {
        try {
            return (T) JacksonUtils.enhancedObjectMapper().readValue(str, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
