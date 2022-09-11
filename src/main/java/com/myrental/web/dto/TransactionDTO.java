package com.myrental.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myrental.web.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Data
@AllArgsConstructor
public class TransactionDTO implements Serializable {
    private Long id;

    private LocalDateTime rentalDate;

    private LocalDateTime returnDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StudentDTO student;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TransactionItemDTO> transactionItems;

    public static TransactionDTO of(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getRentalDate(),
                transaction.getReturnDate(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt(),
                null,
                null
        );
    }

    public static final Comparator<TransactionDTO> SORT_BY_ID_ASC = (o1, o2) -> {
        if (o1.getId() - o2.getId() < 0) {
            return -1;
        } else if (o1.getId() - o2.getId() > 0) {
            return 1;
        } else {
            return 0;
        }
    };
}