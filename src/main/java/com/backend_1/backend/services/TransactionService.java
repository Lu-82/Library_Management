package com.backend_1.backend.services;

import com.backend_1.backend.entities.Book;
import com.backend_1.backend.entities.Transaction;
import com.backend_1.backend.entities.User;
import com.backend_1.backend.repositories.BookRepository;
import com.backend_1.backend.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class TransactionService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction issueBook(User user, Book book) {
        // 1. Check if the book is actually available
        if (book.getAvailableQuantity() <= 0) {
            throw new RuntimeException("No copies available for this book!");
        }

        // 2. Create and set up the transaction record
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setBook(book);
        transaction.setIssueDate(LocalDate.now()); // Today's date
        transaction.setDueDate(LocalDate.now().plusDays(14)); //  in 14 days

        // 3. Update the book's inventory
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);

        // 4. Save the transaction to the database
        return transactionRepository.save(transaction);
    }

    public Transaction returnBook(Long transactionId) {
        // 1. Find the transaction or throw an error if it doesn't exist
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // 2. Mark the return date as today
        transaction.setReturnDate(LocalDate.now());

        // 3. Update the book's availability (increase by 1)
        Book book = transaction.getBook();
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        bookRepository.save(book);

        // 4. Calculate the fine ($2.00 per day late)
        if (transaction.getReturnDate().isAfter(transaction.getDueDate())) {
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(
                    transaction.getDueDate(),
                    transaction.getReturnDate()
            );
            transaction.setFineAmount(daysLate * 2.0);
        } else {
            transaction.setFineAmount(0.0);
        }

        return transactionRepository.save(transaction);
    }

    public java.util.List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public java.util.List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
}