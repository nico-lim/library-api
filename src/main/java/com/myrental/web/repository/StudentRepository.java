package com.myrental.web.repository;

import com.myrental.web.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // Using JPA Query Creations
    Optional<Student> findFirstByEmail(String email);

    // ---
}
