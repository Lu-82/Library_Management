package com.backend_1.backend.services;

import com.backend_1.backend.entities.Book;
import com.backend_1.backend.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Logic to add a new book
    public Book addBook(Book book) {
        // When adding for the first time, all copies are available
        book.setAvailableQuantity(book.getTotalQuantity());
        return bookRepository.save(book);
    }

    // Logic to see all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> searchBooksByTitle(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Book> searchBooksByAuthor(String keyword) {
        return bookRepository.findByAuthorContainingIgnoreCase(keyword);
    }
}