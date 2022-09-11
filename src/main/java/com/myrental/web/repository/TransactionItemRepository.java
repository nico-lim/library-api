package com.myrental.web.repository;

import com.myrental.web.model.Book;
import com.myrental.web.model.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long> {
    // Using JPA Query Creations
    List<Book> findByBook(Book book);

    List<TransactionItem> findAllByTransactionIdIn(Collection<Long> ids);
    // ---
}
