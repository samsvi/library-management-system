package com.library.librarymanagement.controller.web;

import com.library.librarymanagement.dto.request.book.BookCreateRequestDto;
import com.library.librarymanagement.dto.response.book.BookDetailResponseDto;
import com.library.librarymanagement.dto.response.book.BookResponseDto;
import com.library.librarymanagement.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("books")
public class BookWebController {

    private final BookService bookService;

    public BookWebController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String listBooks(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(defaultValue = "title") String sort,
                            @RequestParam(defaultValue = "asc") String dir) {

        Sort.Direction direction = dir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));
        Page<BookResponseDto> bookPage = bookService.getAllBooks(pageable);

        model.addAttribute("bookPage", bookPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        model.addAttribute("reverseDir", dir.equals("asc") ? "desc" : "asc");

        return "books/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new BookCreateRequestDto());
        return "books/create";
    }

    @PostMapping
    public String createBook(@Valid @ModelAttribute("book") BookCreateRequestDto bookCreateRequestDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "books/create";
        }

        try {
            BookResponseDto createdBook = bookService.addBook(bookCreateRequestDto);
            redirectAttributes.addFlashAttribute("successMessage", "Book created successfully!");
            return "redirect:/books/" + createdBook.getId();
        } catch (Exception e) {
            bindingResult.reject("error.book.create", "Error creating book: " + e.getMessage());
            return "books/create";
        }
    }

    @GetMapping("/{id}")
    public String showBookDetail(@PathVariable Long id, Model model) {
        try {
            BookDetailResponseDto book = bookService.getBook(id);
            model.addAttribute("book", book);
            return "books/detail";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Book not found");
            return "books/error";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            BookCreateRequestDto formDto = bookService.getBookEditDto(id);
            model.addAttribute("book", formDto);
            model.addAttribute("bookId", id);
            return "books/edit";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Book not found");
            return "books/error";
        }
    }

    @PostMapping("/{id}")
    public String updateBook(@PathVariable Long id,
                             @Valid @ModelAttribute("book") BookCreateRequestDto bookCreateRequestDto,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("bookId", id);
            return "books/edit";
        }

        try {
            bookService.updateBook(id, bookCreateRequestDto);
            redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully!");
            return "redirect:/books/" + id;
        } catch (Exception e) {
            bindingResult.reject("error.book.update", "Error updating book: " + e.getMessage());
            model.addAttribute("bookId", id);
            return "books/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting book: " + e.getMessage());
        }
        return "redirect:/books";
    }
}
