package com.myrental.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateTransactionRequestBody {
    @JsonProperty("student_id")
    @NotNull
    private Long studentId;

    @JsonProperty("rental_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime rentalDate;

    @JsonProperty("books")
    @NotNull
    @Size(min = 1)
    private List<Long> books;
}
