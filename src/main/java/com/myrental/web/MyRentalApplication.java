package com.myrental.web;

import com.myrental.web.model.Book;
import com.myrental.web.model.Student;
import com.myrental.web.model.Transaction;
import com.myrental.web.model.TransactionItem;
import com.myrental.web.repository.BookRepository;
import com.myrental.web.repository.StudentRepository;
import com.myrental.web.repository.TransactionItemRepository;
import com.myrental.web.repository.TransactionRepository;
import com.myrental.web.util.JpaEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootApplication
public class MyRentalApplication {

	private static final Logger log = LoggerFactory.getLogger(MyRentalApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MyRentalApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDatabase(StudentRepository studentRepository, TransactionRepository transactionRepository, BookRepository bookRepository, TransactionItemRepository transactionItemRepository) {
		return args -> {
			Student student1 = new Student("Nico Limbara", "nico@example.com", LocalDateTime.now(), LocalDateTime.now());
			Student student2 = new Student("Erwin", "erwin@example.com", LocalDateTime.now(), LocalDateTime.now());

			Book book1 = new Book("The Rust Programming Language", "Steve Klabnik", LocalDateTime.now(), LocalDateTime.now());
			Book book2 = new Book("Clean Code", "Robert C Martin", LocalDateTime.now(), LocalDateTime.now());

			Transaction transaction1 = new Transaction(LocalDateTime.now(), null, LocalDateTime.now(), LocalDateTime.now(), student1);
			TransactionItem transactionItem1 = new TransactionItem(transaction1, book1);
			TransactionItem transactionItem2 = new TransactionItem(transaction1, book2);

			bookRepository.save(book1);
			bookRepository.save(book2);
			studentRepository.save(student2);
			studentRepository.save(student1);
			transactionRepository.save(transaction1);
			transactionItemRepository.save(transactionItem1);
			transactionItemRepository.save(transactionItem2);
		};
	}
}
