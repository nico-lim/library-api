package com.myrental.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "rental_date", nullable = false)
    private LocalDateTime rentalDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "student_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "student_transaction_fk"
            )
    )
    private Student student;

    @OneToMany(
            mappedBy = "transaction",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<TransactionItem> transactionItems = new ArrayList<>();

    public Transaction(LocalDateTime rentalDate, @Nullable LocalDateTime returnDate, LocalDateTime createdAt, LocalDateTime updatedAt, Student student) {
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.student = student;
    }

    public void addTransactionItem(TransactionItem transactionItem) {
        if (!this.transactionItems.contains(transactionItem)) {
            this.transactionItems.add(transactionItem);
            transactionItem.setTransaction(this);
        }
    }
}
