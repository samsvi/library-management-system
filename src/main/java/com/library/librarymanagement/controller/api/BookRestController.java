package com.library.librarymanagement.controller.api;

import com.library.librarymanagement.dto.request.book.BookCreateRequestDto;
import com.library.librarymanagement.dto.response.book.BookDetailResponseDto;
import com.library.librarymanagement.dto.response.book.BookResponseDto;
import com.library.librarymanagement.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        List<BookResponseDto> books = bookService.getAllBooks();

        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<BookResponseDto> addBook(@Valid @RequestBody BookCreateRequestDto bookCreateRequestDto) {
        BookResponseDto bookDto = bookService.addBook(bookCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDetailResponseDto> getBookById(@PathVariable Long id) {
        BookDetailResponseDto bookDetail = bookService.getBook(id);

        return ResponseEntity.ok(bookDetail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long id, @Valid @RequestBody BookCreateRequestDto bookCreateRequestDto) {
        BookResponseDto updatedBook = bookService.updateBook(id, bookCreateRequestDto);

        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);

        return ResponseEntity.noContent().build();
    }
}
