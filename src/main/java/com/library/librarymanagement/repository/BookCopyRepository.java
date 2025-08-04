package com.library.librarymanagement.repository;

import com.library.librarymanagement.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

    List<BookCopy> findAllByBookId(Long id);

    Optional<BookCopy> findByIdAndBookId(Long id, Long bookId);
}
