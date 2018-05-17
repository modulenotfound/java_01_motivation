package com.modulenotfound.motivation.rest;

import lombok.Data;

@Data
public class RestError {

    public static final int UNKNOWN_KID_ID = 100;

    private int code;
    private String message;

    public RestError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
