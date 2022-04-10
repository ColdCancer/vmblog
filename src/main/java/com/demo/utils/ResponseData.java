package com.demo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@ToString
public class ResponseData {
    private final Integer code;
    private final String message;
    private Map<String, Object> data = null;
}
