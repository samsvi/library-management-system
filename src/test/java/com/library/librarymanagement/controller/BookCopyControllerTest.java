package com.library.librarymanagement.controller;

import com.library.librarymanagement.controller.api.BookCopyRestController;
import com.library.librarymanagement.dto.response.bookCopy.BookCopyDetailResponseDto;
import com.library.librarymanagement.dto.response.bookCopy.BookCopyResponseDto;
import com.library.librarymanagement.service.BookCopyService;
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

@WebMvcTest(BookCopyRestController.class)
public class BookCopyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public BookCopyService bookCopyService() {
            return Mockito.mock(BookCopyService.class);
        }
    }

    @Autowired
    private BookCopyService bookCopyService;

    @Test
    void getBookCopies_shouldReturnOk() throws Exception {
        BookCopyResponseDto dto = new BookCopyResponseDto();
        dto.setId(1L);

        Mockito.when(bookCopyService.getBookCopies(1L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/books/1/copies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void addBookCopy_shouldReturnCreated() throws Exception {
        BookCopyDetailResponseDto dto = new BookCopyDetailResponseDto();
        dto.setId(1L);
        dto.setAvailable(true);

        Mockito.when(bookCopyService.addBookCopy(1L)).thenReturn(dto);

        mockMvc.perform(post("/api/books/1/copies"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    void updateBookCopy_shouldReturnOk() throws Exception {
        BookCopyDetailResponseDto dto = new BookCopyDetailResponseDto();
        dto.setId(1L);
        dto.setAvailable(false);

        Mockito.when(bookCopyService.updateBookCopy(eq(1L), eq(1L), any())).thenReturn(dto);

        mockMvc.perform(put("/api/books/1/copies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "available": false
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.available").value(false));
    }
}
