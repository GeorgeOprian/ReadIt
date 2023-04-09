package com.dis.readit.mapper;

import com.dis.readit.dtos.output.book.BookDto;
import com.dis.readit.dtos.output.book.BookListDto;
import com.dis.readit.model.book.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {


	@Mapping(target = "thumbnail", source = "thumbnail")
	BookListDto mapToListDto(Book book);

	@Mapping(target = "thumbnail", source = "thumbnail")
	BookDto mapToDto(Book book);
}
