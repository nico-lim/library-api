package com.myrental.web.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorException extends RuntimeException {
    protected HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public ErrorException(String message) {
        super(message);
    }

    public ErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorException(Throwable cause) {
        super(cause);
    }

    public ErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
