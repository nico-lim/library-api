package com.myrental.web.controller;

import com.myrental.web.dto.*;
import com.myrental.web.model.Book;
import com.myrental.web.model.Student;
import com.myrental.web.model.Transaction;
import com.myrental.web.model.TransactionItem;
import com.myrental.web.repository.BookRepository;
import com.myrental.web.repository.TransactionItemRepository;
import com.myrental.web.repository.TransactionRepository;
import com.myrental.web.service.RentService;
import com.myrental.web.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private RentService rentService;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionItemRepository transactionItemRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping()
    @Cacheable(value = "transactionCache")
    public ApiResponse all() {
        List<Transaction> transactions = transactionRepository.findAllWithStudent();
        List<Long> transactionIds = transactions.stream().map(transaction -> transaction.getId()).collect(Collectors.toList());

        List<TransactionItem> transactionItems = transactionItemRepository.findAllByTransactionIdIn(transactionIds);
        Set<Long> uniqueBookId = transactionItems.stream().map(transactionItem -> transactionItem.getBook().getId()).collect(Collectors.toSet());

        Map<Long, BookDTO> bookDTOMap = bookRepository.findAllById(uniqueBookId).stream().collect(Collectors.toMap(Book::getId, BookDTO::of));

        Map<Long, List<TransactionItem>> groupedTransactionItems = transactionItems.stream().collect(Collectors.groupingBy(transactionItem -> transactionItem.getTransaction().getId()));

        List<TransactionDTO> transactionDTOS = transactions.stream().map(transaction -> {
            TransactionDTO transactionDTO = TransactionDTO.of(transaction);
            List<TransactionItemDTO> transactionItemDTOS = groupedTransactionItems.getOrDefault(transactionDTO.getId(), new ArrayList<>())
                    .stream()
                    .map(transactionItem -> {
                        BookDTO bookDTO = bookDTOMap.getOrDefault(transactionItem.getBook().getId(), null);
                        TransactionItemDTO transactionItemDTO = TransactionItemDTO.of(transactionItem);
                        transactionItemDTO.setBook(bookDTO);

                        return transactionItemDTO;
                    }).collect(Collectors.toList());

            transactionDTO.setStudent(StudentDTO.of(transaction.getStudent()));
            transactionDTO.setTransactionItems(transactionItemDTOS);

            return transactionDTO;
        }).sorted(TransactionDTO.SORT_BY_ID_ASC).collect(Collectors.toList());

        return ResponseHandler.generateResponse(
                "success",
                HttpStatus.OK,
                transactionDTOS
        );
    }

    @PostMapping()
    @CacheEvict(value = "transactionCache", allEntries = true)
    public ApiResponse create(@Valid @RequestBody CreateTransactionRequestBody body) {
        Transaction transaction = rentService.rent(body);

        return ResponseHandler.generateResponse(
                "transaction created!",
                HttpStatus.OK,
                transaction
        );
    }

    @PutMapping("{id}")
    @CacheEvict(value = "transactionCache", allEntries = true)
    public ApiResponse update(@Valid @RequestBody UpdateTransactionRequestBody body, @PathVariable Long id) {
        Transaction transaction = rentService.updateRent(body, id);

        return ResponseHandler.generateResponse(
                "transaction updated!",
                HttpStatus.OK,
                transaction
        );
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "transactionCache", allEntries = true)
    public ApiResponse delete(@PathVariable Long id) {
        Transaction transaction = rentService.deleteRent(id);

        return ResponseHandler.generateResponse(
                "transaction deleted!",
                HttpStatus.OK,
                transaction
        );
    }
}
