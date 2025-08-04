package com.library.librarymanagement.dto.request.bookCopy;

import jakarta.validation.constraints.NotNull;

public class BookCopyUpdateRequestDto {

    @NotNull
    private boolean available;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
