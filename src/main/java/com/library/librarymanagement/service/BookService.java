package com.library.librarymanagement.service;

import com.library.librarymanagement.dto.request.book.BookCreateRequestDto;
import com.library.librarymanagement.dto.response.book.BookDetailResponseDto;
import com.library.librarymanagement.dto.response.book.BookResponseDto;
import com.library.librarymanagement.exception.DuplicateResourceException;
import com.library.librarymanagement.exception.ResourceNotFoundException;
import com.library.librarymanagement.exception.UpdateValidationException;
import com.library.librarymanagement.mapper.BookMapper;
import com.library.librarymanagement.model.Book;
import com.library.librarymanagement.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public BookResponseDto addBook(BookCreateRequestDto requestDto) {

        if (bookRepository.existsByTitle(requestDto.getTitle())) {
            throw new DuplicateResourceException("Book with title " + requestDto.getTitle() + " already exists.");
        }

        if (bookRepository.existsBookByIsbn(requestDto.getIsbn())) {
            throw new DuplicateResourceException("Book with ISBN " + requestDto.getIsbn() + " already exists.");
        }

        Book book = bookMapper.toEntity(requestDto);
        Book savedBook = bookRepository.save(book);

        return bookMapper.toDto(savedBook);
    }

    public Optional<BookResponseDto> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto);
    }

    public BookResponseDto updateBook(Long id, BookCreateRequestDto requestDto) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        List<String> errors = new ArrayList<>();

        /*
         * Validate and update fields only if they are provided in the request.
         */
        validateAndUpdateTitle(requestDto.getTitle(), book, errors);
        validateAndUpdateIsbn(requestDto.getIsbn(), book, errors);
        validateAndUpdateAuthor(requestDto.getAuthor(), book, errors);
        validateAndUpdatePublishedYear(requestDto.getPublishedYear(), book, errors);

        if (!errors.isEmpty()) {
            throw new UpdateValidationException("Validation error", errors);
        }

        Book updatedBook = bookRepository.save(book);
        return bookMapper.toDto(updatedBook);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + id + " not found"));

        bookRepository.delete(book);
    }

    public BookDetailResponseDto getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));

        return bookMapper.toDetailDto(book);
    }

    private void validateAndUpdateTitle(String newTitle, Book book, List<String> errors) {
        if (newTitle == null) return;

        if (newTitle.isBlank()) {
            errors.add("Title cannot be blank.");
        } else if (bookRepository.existsByTitle(newTitle) && !book.getTitle().equals(newTitle)) {
            errors.add("Book with title " + newTitle + " already exists.");
        } else {
            book.setTitle(newTitle);
        }
    }

    private void validateAndUpdateIsbn(String newIsbn, Book book, List<String> errors) {
        if (newIsbn == null) return;

        if (newIsbn.isBlank()) {
            errors.add("ISBN cannot be blank.");
        } else if (bookRepository.existsBookByIsbn(newIsbn) && !book.getIsbn().equals(newIsbn)) {
            errors.add("Book with ISBN " + newIsbn + " already exists.");
        } else {
            book.setIsbn(newIsbn);
        }
    }

    private void validateAndUpdateAuthor(String newAuthor, Book book, List<String> errors) {
        if (newAuthor == null) return;

        if (newAuthor.isBlank()) {
            errors.add("Author cannot be blank.");
        } else {
            book.setAuthor(newAuthor);
        }
    }

    private void validateAndUpdatePublishedYear(Integer newYear, Book book, List<String> errors) {
        if (newYear == null) return;

        book.setPublishedYear(newYear);
    }
}
