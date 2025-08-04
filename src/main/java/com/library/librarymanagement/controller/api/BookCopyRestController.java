package com.library.librarymanagement.controller.api;

import com.library.librarymanagement.dto.request.bookCopy.BookCopyUpdateRequestDto;
import com.library.librarymanagement.dto.response.bookCopy.BookCopyDetailResponseDto;
import com.library.librarymanagement.dto.response.bookCopy.BookCopyResponseDto;
import com.library.librarymanagement.service.BookCopyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookCopyRestController {

    private final BookCopyService bookCopyService;

    public BookCopyRestController(BookCopyService bookCopyService) {
        this.bookCopyService = bookCopyService;
    }

    @GetMapping("/{id}/copies")
    public ResponseEntity<List<BookCopyResponseDto>> getBookCopies(@PathVariable Long id) {
        List<BookCopyResponseDto> bookCopies = bookCopyService.getBookCopies(id);

        return ResponseEntity.ok(bookCopies);
    }

    @PostMapping("/{id}/copies")
    public ResponseEntity<BookCopyDetailResponseDto> addBookCopy(@PathVariable Long id) {
        BookCopyDetailResponseDto createdCopy = bookCopyService.addBookCopy(id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdCopy);
    }

    @PutMapping("/{id}/copies/{copyId}")
    public ResponseEntity<BookCopyDetailResponseDto> updateBookCopy(@PathVariable Long id, @PathVariable Long copyId, @Valid @RequestBody BookCopyUpdateRequestDto bookCopyUpdateRequestDto) {
        BookCopyDetailResponseDto updatedCopy = bookCopyService.updateBookCopy(id, copyId, bookCopyUpdateRequestDto);

        return ResponseEntity.ok(updatedCopy);
    }
}
