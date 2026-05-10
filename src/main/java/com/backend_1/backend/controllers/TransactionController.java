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

    @PostMapping("/issue")
    public Transaction issueBook(@RequestParam Long userId, @RequestParam Long bookId) {
        // 1. Find the User and Book (we use .orElseThrow in case the ID is wrong)
        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        // 2. Pass them to the service to do the heavy lifting
        return transactionService.issueBook(user, book);
    }
    @PutMapping("/return/{id}")
    public Transaction returnBook(@PathVariable Long id) {
        // We use the ID from the URL to tell the service which record to update
        return transactionService.returnBook(id);
    }
}