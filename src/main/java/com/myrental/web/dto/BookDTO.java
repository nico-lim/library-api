package com.myrental.web.dto;

import com.myrental.web.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookDTO implements Serializable {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BookDTO of(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );
    }
}
