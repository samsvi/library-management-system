package com.library.librarymanagement.repository;

import com.library.librarymanagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsBookByIsbn(String isbn);
    boolean existsBookByTitle(String title);

    boolean existsByTitle(String title);
}
