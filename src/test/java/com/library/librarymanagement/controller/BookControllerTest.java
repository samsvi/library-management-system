package com.library.librarymanagement.controller;

import com.library.librarymanagement.controller.api.BookRestController;
import com.library.librarymanagement.dto.response.book.BookDetailResponseDto;
import com.library.librarymanagement.dto.response.book.BookResponseDto;
import com.library.librarymanagement.exception.DuplicateResourceException;
import com.library.librarymanagement.exception.ResourceNotFoundException;
import com.library.librarymanagement.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public BookService bookService() {
            return Mockito.mock(BookService.class);
        }
    }

    @Autowired
    private BookService bookService;

    @Test
    void getAllBooks_shouldReturnOk() throws Exception {
        BookResponseDto book = new BookResponseDto();
        book.setId(1L);
        book.setTitle("Clean Code");

        Mockito.when(bookService.getAllBooks()).thenReturn(List.of(book));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Clean Code"));
    }

    @Test
    void addBook_shouldReturnConflict_whenDuplicateIsbn() throws Exception {
        Mockito.when(bookService.addBook(any()))
                .thenThrow(new DuplicateResourceException("Book with ISBN 9780132350884 already exists."));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "title": "Clean Code",
                          "isbn": "9780132350884",
                          "author": "Robert C. Martin",
                          "publishedYear": 2008
                        }
                    """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Book with ISBN 9780132350884 already exists."));
    }

    @Test
    void getBookById_shouldReturnBook() throws Exception {
        BookDetailResponseDto detail = new BookDetailResponseDto();
        detail.setId(1L);
        detail.setTitle("Clean Code");

        Mockito.when(bookService.getBook(1L)).thenReturn(detail);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    void getBookById_shouldReturnNotFound() throws Exception {
        Mockito.when(bookService.getBook(99L))
                .thenThrow(new ResourceNotFoundException("Book not found with ID: 99"));

        mockMvc.perform(get("/api/books/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book not found with ID: 99"));
    }

    @Test
    void deleteBook_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(bookService).deleteBook(1L);
    }

    @Test
    void deleteBook_shouldReturnNotFound() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException("Book with ID 99 not found"))
                .when(bookService).deleteBook(99L);

        mockMvc.perform(delete("/api/books/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book with ID 99 not found"));
    }

    @Test
    void updateBook_shouldReturnUpdatedBook() throws Exception {
        BookResponseDto response = new BookResponseDto();
        response.setId(1L);
        response.setTitle("Updated");

        Mockito.when(bookService.updateBook(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "title": "Updated",
                          "isbn": "9780132350884",
                          "author": "Bob",
                          "publishedYear": 2020
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated"));
    }
}
