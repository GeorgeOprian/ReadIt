package com.dis.readit.mapper;

import com.dis.readit.dtos.output.CategoryDto;
import com.dis.readit.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriesMapper {

	CategoryDto mapToDto(Category category);
}
