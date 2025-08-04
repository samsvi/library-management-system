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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookCopyServiceTest {

    @Mock
    private BookCopyRepository bookCopyRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookCopyMapper bookCopyMapper;

    @InjectMocks
    private BookCopyService bookCopyService;

    @Test
    void getBookCopies_shouldReturnList() {
        Long bookId = 1L;

        BookCopy copy = new BookCopy();
        copy.setId(1L);

        BookCopyResponseDto dto = new BookCopyResponseDto();
        dto.setId(1L);

        Mockito.when(bookCopyRepository.findAllByBookId(bookId)).thenReturn(List.of(copy));
        Mockito.when(bookCopyMapper.toDto(copy)).thenReturn(dto);

        List<BookCopyResponseDto> result = bookCopyService.getBookCopies(bookId);

        assertEquals(1, result.size());
        assertEquals(1L, result.getFirst().getId());
    }

    @Test
    void addBookCopy_shouldCreateAndReturnDetailDto() {
        Long bookId = 1L;

        Book book = new Book();
        book.setId(bookId);

        BookCopy copy = new BookCopy();
        copy.setBook(book);
        copy.setAvailable(true);

        BookCopy savedCopy = new BookCopy();
        savedCopy.setId(1L);
        savedCopy.setAvailable(true);

        BookCopyDetailResponseDto dto = new BookCopyDetailResponseDto();
        dto.setId(1L);
        dto.setAvailable(true);

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(bookCopyRepository.save(any(BookCopy.class))).thenReturn(savedCopy);
        Mockito.when(bookCopyMapper.toDetailDto(savedCopy)).thenReturn(dto);

        BookCopyDetailResponseDto result = bookCopyService.addBookCopy(bookId);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertTrue(result.isAvailable());
    }

    @Test
    void addBookCopy_shouldThrow_whenBookNotFound() {
        Long bookId = 1L;

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookCopyService.addBookCopy(bookId));
    }

    @Test
    void updateBookCopy_shouldUpdateAndReturnDto() {
        Long bookId = 1L;
        Long copyId = 2L;

        BookCopyUpdateRequestDto requestDto = new BookCopyUpdateRequestDto();
        requestDto.setAvailable(false);

        BookCopy existingCopy = new BookCopy();
        existingCopy.setId(copyId);
        existingCopy.setAvailable(true);

        BookCopy savedCopy = new BookCopy();
        savedCopy.setId(copyId);
        savedCopy.setAvailable(false);

        BookCopyDetailResponseDto responseDto = new BookCopyDetailResponseDto();
        responseDto.setId(copyId);
        responseDto.setAvailable(false);

        Mockito.when(bookCopyRepository.findByIdAndBookId(copyId, bookId)).thenReturn(Optional.of(existingCopy));
        Mockito.when(bookCopyRepository.save(existingCopy)).thenReturn(savedCopy);
        Mockito.when(bookCopyMapper.toDetailDto(savedCopy)).thenReturn(responseDto);

        BookCopyDetailResponseDto result = bookCopyService.updateBookCopy(bookId, copyId, requestDto);

        assertNotNull(result);
        assertFalse(result.isAvailable());
        assertEquals(copyId, result.getId());
    }

    @Test
    void updateBookCopy_shouldThrow_whenCopyNotFound() {
        Long bookId = 1L;
        Long copyId = 2L;

        BookCopyUpdateRequestDto requestDto = new BookCopyUpdateRequestDto();
        requestDto.setAvailable(true);

        Mockito.when(bookCopyRepository.findByIdAndBookId(copyId, bookId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookCopyService.updateBookCopy(bookId, copyId, requestDto));
    }
}
