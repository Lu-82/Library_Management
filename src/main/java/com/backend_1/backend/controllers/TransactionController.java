package com.backend_1.backend.controllers;

import com.backend_1.backend.entities.Book;
import com.backend_1.backend.entities.Transaction;
import com.backend_1.backend.entities.User;
import com.backend_1.backend.repositories.BookRepository;
import com.backend_1.backend.repositories.UserRepository;
import com.backend_1.backend.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public java.util.List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/my-history")
    public java.util.List<Transaction> getMyHistory(org.springframework.security.core.Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return transactionService.getTransactionsByUserId(user.getId());
    }

    @PostMapping("/borrow/{bookId}")
    public Transaction issueBook(@PathVariable Long bookId, org.springframework.security.core.Authentication authentication) {
        // 1. Find the User from Authentication
        User user = (User) authentication.getPrincipal();
        
        // 2. Find the Book
        Book book = bookRepository.findById(bookId).orElseThrow();

        // 3. Pass them to the service to do the heavy lifting
        return transactionService.issueBook(user, book);
    }

    @PostMapping("/return/{id}")
    public Transaction returnBook(@PathVariable Long id) {
        // We use the ID from the URL to tell the service which record to update
        return transactionService.returnBook(id);
    }
}