package com.backend_1.backend.repositories;

import com.backend_1.backend.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // This helps us find books currently borrowed by a specific user
    List<Transaction> findByUserIdAndReturnDateIsNull(Long userId);

    // This helps us find all transactions for a specific user
    List<Transaction> findByUserId(Long userId);
}