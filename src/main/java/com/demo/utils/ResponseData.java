package com.demo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@AllArgsConstructor
@ToString
public class ResponseData {
    private Integer code;
    private String message;
    private Map<String, Object> data = null;

    public ResponseData(ResponseState state, Map<String, Object> data) {
        this.code = state.getCode();
        this.message = state.getDesc();
        this.data = data;
    }
}
