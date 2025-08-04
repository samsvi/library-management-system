package com.library.librarymanagement.dto.request.book;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.ISBN;

public class BookCreateRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "ISBN is required")
    @ISBN(type = ISBN.Type.ANY, message = "Invalid ISBN format")
    private String isbn;

    @NotNull(message = "Published year is required")
    @Min(value = 1, message = "Year must be after 1")
    @Max(value = 2025, message = "Year must be before 2026")
    private Integer publishedYear;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }
}
