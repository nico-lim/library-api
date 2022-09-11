package com.myrental.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myrental.web.model.Book;
import com.myrental.web.model.TransactionItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class TransactionItemDTO implements Serializable {
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BookDTO book;

    public static TransactionItemDTO of(TransactionItem transactionItem) {
        return new TransactionItemDTO(
                transactionItem.getId(),
                null
        );
    }
}
