package com.library.librarymanagement.controller.api;

import com.library.librarymanagement.dto.request.bookCopy.BookCopyUpdateRequestDto;
import com.library.librarymanagement.dto.response.bookCopy.BookCopyDetailResponseDto;
import com.library.librarymanagement.dto.response.bookCopy.BookCopyResponseDto;
import com.library.librarymanagement.service.BookCopyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
@Tag(name = "Book Copies", description = "Endpoints related to book copies")
public class BookCopyRestController {

    private final BookCopyService bookCopyService;

    public BookCopyRestController(BookCopyService bookCopyService) {
        this.bookCopyService = bookCopyService;
    }

    @Operation(summary = "Get all copies of a book", description = "Retrieve a list of all copies for a specific book by its ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved book copies")
    @GetMapping("/{id}/copies")
    public ResponseEntity<List<BookCopyResponseDto>> getBookCopies(@PathVariable Long id) {
        List<BookCopyResponseDto> bookCopies = bookCopyService.getBookCopies(id);

        return ResponseEntity.ok(bookCopies);
    }

    @Operation(summary = "Get a specific book copy", description = "Retrieve details of a specific book copy by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully retrieved book copy"),
            @ApiResponse(responseCode = "404", description = "Book copy not found")
    })
    @PostMapping("/{id}/copies")
    public ResponseEntity<BookCopyDetailResponseDto> addBookCopy(@PathVariable Long id) {
        BookCopyDetailResponseDto createdCopy = bookCopyService.addBookCopy(id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdCopy);
    }

    @Operation(summary = "Update a book copy", description = "Update the details of a specific book copy by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated book copy"),
            @ApiResponse(responseCode = "404", description = "Book copy not found")
    })
    @PutMapping("/{id}/copies/{copyId}")
    public ResponseEntity<BookCopyDetailResponseDto> updateBookCopy(@PathVariable Long id, @PathVariable Long copyId, @Valid @RequestBody BookCopyUpdateRequestDto bookCopyUpdateRequestDto) {
        BookCopyDetailResponseDto updatedCopy = bookCopyService.updateBookCopy(id, copyId, bookCopyUpdateRequestDto);

        return ResponseEntity.ok(updatedCopy);
    }
}
