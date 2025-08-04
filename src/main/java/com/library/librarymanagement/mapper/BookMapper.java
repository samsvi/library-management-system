package com.library.librarymanagement.mapper;

import com.library.librarymanagement.dto.request.book.BookCreateRequestDto;
import com.library.librarymanagement.dto.response.book.BookDetailResponseDto;
import com.library.librarymanagement.dto.response.book.BookResponseDto;
import com.library.librarymanagement.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity(BookCreateRequestDto dto);

    BookResponseDto toDto(Book book);

    @Mapping(source = "copies", target = "copies")
    BookDetailResponseDto toDetailDto(Book book);
}
