package com.myrental.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class CreateStudentRequestBody {
    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("email")
    @NotNull
    @Email
    private String email;
}
