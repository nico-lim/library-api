package com.myrental.web.util;

import com.myrental.web.dto.ApiResponse;
import org.springframework.http.HttpStatus;

public class ResponseHandler {
    public static ApiResponse generateResponse(String message, HttpStatus status, Object responseObj) {
        return new ApiResponse(
                message,
                status,
                responseObj
        );
    }
}
