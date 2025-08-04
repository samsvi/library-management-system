package com.library.librarymanagement.controller.api;

import com.library.librarymanagement.dto.request.book.BookCreateRequestDto;
import com.library.librarymanagement.dto.response.book.BookDetailResponseDto;
import com.library.librarymanagement.dto.response.book.BookResponseDto;
import com.library.librarymanagement.dto.response.error.ApiErrorDto;
import com.library.librarymanagement.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Endpoints for managing books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Get all books", description = "Retrieve a list of all books in the library")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of books")
    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        List<BookResponseDto> books = bookService.getAllBooks();

        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Add a new book", description = "Create a new book in the library")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new book"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "409", description = "Book with the same title or ISBN already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @PostMapping
    public ResponseEntity<BookResponseDto> addBook(@Valid @RequestBody BookCreateRequestDto bookCreateRequestDto) {
        BookResponseDto bookDto = bookService.addBook(bookCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookDto);
    }

    @Operation(summary = "Get book by ID", description = "Retrieve details of a specific book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved book details"),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookDetailResponseDto> getBookById(@PathVariable Long id) {
        BookDetailResponseDto bookDetail = bookService.getBook(id);

        return ResponseEntity.ok(bookDetail);
    }

    @Operation(summary = "Update book by ID", description = "Update details of a specific book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated book details"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long id, @Valid @RequestBody BookCreateRequestDto bookCreateRequestDto) {
        BookResponseDto updatedBook = bookService.updateBook(id, bookCreateRequestDto);

        return ResponseEntity.ok(updatedBook);
    }

    @Operation(summary = "Delete book by ID", description = "Delete a specific book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted book"),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);

        return ResponseEntity.noContent().build();
    }
}
