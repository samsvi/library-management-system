package com.library.librarymanagement.mapper;

import com.library.librarymanagement.dto.response.bookCopy.BookCopyDetailResponseDto;
import com.library.librarymanagement.dto.response.bookCopy.BookCopyResponseDto;
import com.library.librarymanagement.model.BookCopy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {

    BookCopyResponseDto toDto(BookCopy bookCopy);

    @Mapping(target = "bookId", source = "book.id")
    BookCopyDetailResponseDto toDetailDto(BookCopy bookCopy);
}
