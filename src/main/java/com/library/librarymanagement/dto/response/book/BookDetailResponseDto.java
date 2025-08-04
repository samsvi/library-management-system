package com.library.librarymanagement.dto.response.book;

import com.library.librarymanagement.dto.response.bookCopy.BookCopyResponseDto;

import java.util.List;

public class BookDetailResponseDto {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer publishedYear;
    private List<BookCopyResponseDto> copies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<BookCopyResponseDto> getCopies() {
        return copies;
    }

    public void setCopies(List<BookCopyResponseDto> copies) {
        this.copies = copies;
    }
}
