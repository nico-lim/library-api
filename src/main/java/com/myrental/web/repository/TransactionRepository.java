package com.myrental.web.repository;

import com.myrental.web.model.Student;
import com.myrental.web.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Using JPA Query Creations
    List<Transaction> findByStudent(Student student);

    Optional<Transaction> findByStudentAndId(Student student, Long id);

//     ---
    @Query(value = "SELECT t FROM Transaction t JOIN FETCH t.student")
    List<Transaction> findAllWithStudent();
}
