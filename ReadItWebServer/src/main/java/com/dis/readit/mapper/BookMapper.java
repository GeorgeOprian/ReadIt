package com.dis.readit.mapper;

import com.dis.readit.dtos.output.BookDto;
import com.dis.readit.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

	@Mapping(target = "thumbnail", source = "thumbnail")
	BookDto mapToDto(Book book);
}
