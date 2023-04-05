package com.dis.readit.mapper;

import com.dis.readit.dtos.output.book.CategoryDto;
import com.dis.readit.model.book.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriesMapper {

	CategoryDto mapToDto(Category category);
}
