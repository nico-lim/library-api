package com.myrental.web.service;

import com.myrental.web.dto.CreateTransactionRequestBody;
import com.myrental.web.dto.UpdateTransactionRequestBody;
import com.myrental.web.error.NotFoundException;
import com.myrental.web.model.Book;
import com.myrental.web.model.Student;
import com.myrental.web.model.Transaction;
import com.myrental.web.model.TransactionItem;
import com.myrental.web.repository.BookRepository;
import com.myrental.web.repository.StudentRepository;
import com.myrental.web.repository.TransactionItemRepository;
import com.myrental.web.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionItemRepository transactionItemRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public Transaction rent(CreateTransactionRequestBody body) {
        Student student = getStudent(body.getStudentId());

        Transaction transaction = new Transaction(body.getRentalDate(), null, LocalDateTime.now(), LocalDateTime.now(), student);
        List<TransactionItem> transactionItems = body.getBooks().stream().map(bookId -> {
            Optional<Book> optBook = bookRepository.findById(bookId);
            if (optBook.isEmpty()) {
                throw new NotFoundException("book not found!");
            }
            return new TransactionItem(transaction, optBook.get());
        }).collect(Collectors.toList());
        transaction.setTransactionItems(transactionItems);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction updateRent(UpdateTransactionRequestBody body, Long id) {
        Student student = getStudent(body.getStudentId());

        Transaction transaction = getTransaction(student, id);
        transaction.setRentalDate(body.getRentalDate());

        List<TransactionItem> transactionItems = transaction.getTransactionItems();
        List<TransactionItem> transactionItemsToPersist = body.getBooks()
                .stream()
                .map(bookId -> {
                    Optional<Book> optBook = bookRepository.findById(bookId);
                    if(optBook.isEmpty()) {
                        throw new NotFoundException("book not found!");
                    }

                    return new TransactionItem(transaction, optBook.get());
                })
                .collect(Collectors.toList());

        transactionItems.clear();
        transactionItems.addAll(transactionItemsToPersist);

        return transaction;
    }

    @Transactional
    public Transaction deleteRent(Long id) {
        Optional<Transaction> optTransaction = transactionRepository.findById(id);

        if (optTransaction.isEmpty()) {
            throw new NotFoundException("transction not found!");
        }

        transactionRepository.delete(optTransaction.get());

        return optTransaction.get();
    }

    private Student getStudent(Long id) throws NotFoundException {
        Optional<Student> optStudent = studentRepository.findById(id);

        if(optStudent.isEmpty()) {
            throw new NotFoundException("student not found!");
        }

        return optStudent.get();
    }

    private Transaction getTransaction(Student student, Long id) throws NotFoundException {
        Optional<Transaction> optTransaction = transactionRepository.findByStudentAndId(student, id);

        if (optTransaction.isEmpty()) {
            throw  new NotFoundException("transaction not found!");
        }

        return optTransaction.get();
    }
 }
