package com.dis.readit.mapper;

import com.dis.readit.dtos.output.PageDto;
import org.springframework.data.domain.Page;

import java.io.Serializable;


public class PageMapper {

	private PageMapper() {}

	public static <T extends Serializable> PageDto<T> mapToDto(Page<T> page){
		PageDto<T> pageDto = new PageDto<>();

		pageDto.setContent(page.getContent());
		pageDto.setOffset(page.getPageable().getOffset());
		pageDto.setPageNumber(page.getPageable().getPageNumber());
		pageDto.setPageSize(page.getNumberOfElements());
		pageDto.setTotalPages(page.getTotalPages());
		pageDto.setTotalSize(page.getTotalElements());

		return pageDto;
	}

}
