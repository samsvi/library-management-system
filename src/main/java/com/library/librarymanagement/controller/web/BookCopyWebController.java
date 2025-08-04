package com.library.librarymanagement.controller.web;

import com.library.librarymanagement.dto.request.bookCopy.BookCopyUpdateRequestDto;
import com.library.librarymanagement.dto.response.book.BookDetailResponseDto;
import com.library.librarymanagement.dto.response.bookCopy.BookCopyDetailResponseDto;
import com.library.librarymanagement.dto.response.bookCopy.BookCopyResponseDto;
import com.library.librarymanagement.service.BookCopyService;
import com.library.librarymanagement.service.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books/{bookId}/copies")
public class BookCopyWebController {

    private final BookCopyService bookCopyService;
    private final BookService bookService;

    public BookCopyWebController(BookCopyService bookCopyService, BookService bookService) {
        this.bookCopyService = bookCopyService;
        this.bookService = bookService;
    }

    @GetMapping
    public String listBookCopies(@PathVariable Long bookId, Model model) {
        try {
            List<BookCopyResponseDto> copies = bookCopyService.getBookCopies(bookId);
            BookDetailResponseDto book = bookService.getBook(bookId);

            model.addAttribute("copies", copies);
            model.addAttribute("book", book);
            model.addAttribute("bookId", bookId);

            return "books/copies/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error loading book copies: " + e.getMessage());
            return "books/error";
        }
    }

    @PostMapping
    public String addBookCopy(@PathVariable Long bookId, RedirectAttributes redirectAttributes) {
        try {
            BookCopyDetailResponseDto newCopy = bookCopyService.addBookCopy(bookId);
            redirectAttributes.addFlashAttribute("successMessage", "Book copy added successfully!");
            return "redirect:/books/" + bookId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding book copy: " + e.getMessage());
            return "redirect:/books/" + bookId;
        }
    }

    @PostMapping("/{copyId}")
    public String updateBookCopy(@PathVariable Long bookId,
                                 @PathVariable Long copyId,
                                 @Valid @ModelAttribute("copyForm") BookCopyUpdateRequestDto updateDto,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return loadBookDetailPage(bookId, model, updateDto);
        }

        try {
            bookCopyService.updateBookCopy(bookId, copyId, updateDto);
            redirectAttributes.addFlashAttribute("successMessage", "Book copy updated successfully!");
            return "redirect:/books/" + bookId;

        } catch (Exception e) {
            bindingResult.reject("error.copy.update", "Error updating book copy: " + e.getMessage());
            return loadBookDetailPage(bookId, model, updateDto);
        }
    }

    private String loadBookDetailPage(Long bookId, Model model, BookCopyUpdateRequestDto form) {
        BookDetailResponseDto book = bookService.getBook(bookId);
        model.addAttribute("book", book);

        Map<String, BookCopyUpdateRequestDto> copyForms = new HashMap<>();
        for (BookCopyResponseDto copy : book.getCopies()) {
            BookCopyUpdateRequestDto dto = new BookCopyUpdateRequestDto();
            dto.setAvailable(copy.isAvailable());
            copyForms.put(copy.getId().toString(), dto);
        }
        model.addAttribute("copyForms", copyForms);
        return "books/detail";
    }
}
