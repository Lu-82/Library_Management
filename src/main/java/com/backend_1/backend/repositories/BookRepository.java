package com.backend_1.backend.repositories;

import com.backend_1.backend.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>{

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorContainingIgnoreCase(String author);
}
