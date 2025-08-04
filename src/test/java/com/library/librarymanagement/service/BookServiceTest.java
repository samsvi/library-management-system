package com.library.librarymanagement.service;

import com.library.librarymanagement.dto.request.book.BookCreateRequestDto;
import com.library.librarymanagement.dto.response.book.BookDetailResponseDto;
import com.library.librarymanagement.dto.response.book.BookResponseDto;
import com.library.librarymanagement.exception.DuplicateResourceException;
import com.library.librarymanagement.exception.ResourceNotFoundException;
import com.library.librarymanagement.mapper.BookMapper;
import com.library.librarymanagement.model.Book;
import com.library.librarymanagement.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    void testGetAllBooks_shouldReturnList() {
        Book book = new Book();
        book.setId(1L);

        BookResponseDto dto = new BookResponseDto();
        dto.setId(1L);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(List.of(book), pageable, 1);
        Page<BookResponseDto> expectedDtoPage = new PageImpl<>(List.of(dto), pageable, 1);

        Mockito.when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        Mockito.when(bookMapper.toDto(book)).thenReturn(dto);

        Page<BookResponseDto> result = bookService.getAllBooks(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(dto.getId(), result.getContent().getFirst().getId());
    }

    @Test
    void addBook_shouldSaveBookAndReturnDto() {
        BookCreateRequestDto request = new BookCreateRequestDto();
        request.setTitle("Clean Code");
        request.setIsbn("9780132350884");

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setIsbn("9780132350884");

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setTitle("Clean Code");

        BookResponseDto dto = new BookResponseDto();
        dto.setId(1L);
        dto.setTitle("Clean Code");

        Mockito.when(bookRepository.existsByTitle("Clean Code")).thenReturn(false);
        Mockito.when(bookRepository.existsBookByIsbn("9780132350884")).thenReturn(false);
        Mockito.when(bookMapper.toEntity(request)).thenReturn(book);
        Mockito.when(bookRepository.save(book)).thenReturn(savedBook);
        Mockito.when(bookMapper.toDto(savedBook)).thenReturn(dto);

        BookResponseDto result = bookService.addBook(request);

        assertEquals(1L, result.getId());
        assertEquals("Clean Code", result.getTitle());
    }

    @Test
    void addBook_shouldThrowException_whenTitleExists() {
        BookCreateRequestDto request = new BookCreateRequestDto();
        request.setTitle("Existing Book");
        Mockito.when(bookRepository.existsByTitle("Existing Book")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> bookService.addBook(request));
    }

    @Test
    void addBook_shouldThrowException_whenIsbnExists() {
        BookCreateRequestDto request = new BookCreateRequestDto();
        request.setIsbn("9780132350884");
        Mockito.when(bookRepository.existsBookByIsbn("9780132350884")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> bookService.addBook(request));
    }

    @Test
    void updateBook_shouldUpdateAndReturnDto() {
        Long id = 1L;

        BookCreateRequestDto requestDto = new BookCreateRequestDto();
        requestDto.setTitle("Updated Title");
        requestDto.setIsbn("9780132350884");
        requestDto.setAuthor("Updated Author");
        requestDto.setPublishedYear(2021);

        Book existingBook = new Book();
        existingBook.setId(id);
        existingBook.setTitle("Old Title");
        existingBook.setIsbn("1234567890");
        existingBook.setAuthor("Old Author");
        existingBook.setPublishedYear(2000);

        Book savedBook = new Book();
        savedBook.setId(id);
        savedBook.setTitle("Updated Title");

        BookResponseDto responseDto = new BookResponseDto();
        responseDto.setId(id);
        responseDto.setTitle("Updated Title");

        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(existingBook));
        Mockito.when(bookRepository.existsByTitle("Updated Title")).thenReturn(false);
        Mockito.when(bookRepository.existsBookByIsbn("9780132350884")).thenReturn(false);
        Mockito.when(bookRepository.save(existingBook)).thenReturn(savedBook);
        Mockito.when(bookMapper.toDto(savedBook)).thenReturn(responseDto);

        BookResponseDto result = bookService.updateBook(id, requestDto);

        assertEquals("Updated Title", result.getTitle());
        Mockito.verify(bookRepository).save(existingBook);
    }

    @Test
    void updateBook_shouldThrowException_whenBookNotFound() {
        Long id = 1L;
        BookCreateRequestDto requestDto = new BookCreateRequestDto();

        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.updateBook(id, requestDto));
    }

    @Test
    void deleteBook_shouldDelete_whenBookExists() {
        Long id = 1L;
        Book book = new Book();
        book.setId(id);

        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        bookService.deleteBook(id);

        Mockito.verify(bookRepository).delete(book);
    }

    @Test
    void deleteBook_shouldThrowException_whenBookNotFound() {
        Long id = 1L;

        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(id));
    }

    @Test
    void getBook_shouldReturnDetailDto_whenBookExists() {
        Long id = 1L;
        Book book = new Book();
        book.setId(id);

        BookDetailResponseDto detailDto = new BookDetailResponseDto();
        detailDto.setId(id);
        detailDto.setTitle("Some Book");

        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.toDetailDto(book)).thenReturn(detailDto);

        BookDetailResponseDto result = bookService.getBook(id);

        assertEquals("Some Book", result.getTitle());
    }

    @Test
    void getBook_shouldThrowException_whenBookNotFound() {
        Long id = 1L;

        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getBook(id));
    }
}
