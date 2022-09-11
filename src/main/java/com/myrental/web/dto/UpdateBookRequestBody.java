package com.myrental.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateBookRequestBody {
    @JsonProperty("title")
    @NotNull
    private String title;

    @JsonProperty("author")
    @NotNull
    private String author;
}
