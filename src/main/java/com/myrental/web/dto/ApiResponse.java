package com.myrental.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ApiResponse implements Cloneable, Serializable {
    private String message;
    private HttpStatus status;
    private Object data;
}
