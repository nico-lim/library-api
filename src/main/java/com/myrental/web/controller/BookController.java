package com.myrental.web.controller;

import com.myrental.web.dto.ApiResponse;
import com.myrental.web.dto.CreateBookRequestBody;
import com.myrental.web.dto.UpdateBookRequestBody;
import com.myrental.web.error.ErrorException;
import com.myrental.web.error.NotFoundException;
import com.myrental.web.model.Book;
import com.myrental.web.repository.BookRepository;
import com.myrental.web.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(path = "/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("")
    public ApiResponse all() {
        ErrorException e = new ErrorException("r");
        return ResponseHandler.generateResponse(
                "success",
                HttpStatus.OK,
                bookRepository.findAll()
        );
    }

    @PostMapping("")
    public ApiResponse create(@Valid @RequestBody CreateBookRequestBody body) {
        Book newBook = bookRepository.save(new Book(body.getTitle(), body.getAuthor(), LocalDateTime.now(), LocalDateTime.now()));

        return ResponseHandler.generateResponse(
                "success",
                HttpStatus.OK,
                newBook
        );
    }

    @PutMapping("/{id}")
    public ApiResponse update(@Valid @RequestBody UpdateBookRequestBody body, @PathVariable Long id) {
        Book book = getBook(id);
        book.setTitle(body.getTitle());
        book.setAuthor(body.getAuthor());
        book.setUpdatedAt(LocalDateTime.now());
        book = bookRepository.save(book);

        return ResponseHandler.generateResponse(
                "book updated!",
                HttpStatus.OK,
                book
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        Book book = getBook(id);
        bookRepository.delete(book);

        return ResponseHandler.generateResponse(
                "book deleted!",
                HttpStatus.OK,
                book
        );
    }

    @GetMapping("{id}")
    public ApiResponse read(@PathVariable Long id) {
        Book book = getBook(id);

        return ResponseHandler.generateResponse(
                "success",
                HttpStatus.OK,
                book
        );
    }

    private Book getBook(Long id) throws NotFoundException {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isEmpty()) {
            throw new NotFoundException("book not found!");
        }

        return optionalBook.get();
    }
}
