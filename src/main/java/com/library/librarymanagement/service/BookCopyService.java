package com.library.librarymanagement.service;

import com.library.librarymanagement.dto.request.bookCopy.BookCopyUpdateRequestDto;
import com.library.librarymanagement.dto.response.bookCopy.BookCopyDetailResponseDto;
import com.library.librarymanagement.dto.response.bookCopy.BookCopyResponseDto;
import com.library.librarymanagement.exception.ResourceNotFoundException;
import com.library.librarymanagement.mapper.BookCopyMapper;
import com.library.librarymanagement.model.Book;
import com.library.librarymanagement.model.BookCopy;
import com.library.librarymanagement.repository.BookCopyRepository;
import com.library.librarymanagement.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;

    private final BookRepository bookRepository;

    private final BookCopyMapper bookCopyMapper;

    public BookCopyService(BookCopyRepository bookCopyRepository, BookRepository bookRepository, BookCopyMapper bookCopyMapper) {
        this.bookCopyRepository = bookCopyRepository;
        this.bookRepository = bookRepository;
        this.bookCopyMapper = bookCopyMapper;
    }

    public List<BookCopyResponseDto> getBookCopies(Long id) {
        return bookCopyRepository.findAllByBookId(id)
                .stream()
                .map(bookCopyMapper::toDto)
                .toList();
    }

    public BookCopyDetailResponseDto addBookCopy(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        BookCopy bookCopy = new BookCopy();
        bookCopy.setBook(book);
        bookCopy.setAvailable(true);

        bookCopy = bookCopyRepository.save(bookCopy);

        return bookCopyMapper.toDetailDto(bookCopy);
    }

    public BookCopyDetailResponseDto updateBookCopy(Long bookId, Long copyId, BookCopyUpdateRequestDto requestDto) {
        BookCopy bookCopy = bookCopyRepository.findByIdAndBookId(copyId, bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book copy not found with id: " + copyId + " for book id: " + bookId));

        bookCopy.setAvailable(requestDto.isAvailable());
        BookCopy updatedBookCopy = bookCopyRepository.save(bookCopy);

        return bookCopyMapper.toDetailDto(updatedBookCopy);
    }
}
