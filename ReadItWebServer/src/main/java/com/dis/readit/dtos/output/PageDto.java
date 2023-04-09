package com.dis.readit.dtos.output;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageDto<T extends Serializable> implements Serializable {
	private List<T> content;

	private Long offset;

	private Integer pageNumber;

	private Integer pageSize;

	private Integer totalPages;

	private Long totalSize;


}
