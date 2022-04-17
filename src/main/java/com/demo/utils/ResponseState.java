package com.demo.utils;

import lombok.Data;

public enum ResponseState {
    SUCCESS(0, "success"),
    FAILURE(-1, "failure"),
    EMPTY(1, "empty");

    private final Integer code;
    private final String desc;

    ResponseState(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
