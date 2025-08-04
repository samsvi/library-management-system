package com.library.librarymanagement.dto.request.bookCopy;

import jakarta.validation.constraints.NotNull;

public class BookCopyUpdateRequestDto {

    @NotNull
    private Boolean available;

    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
