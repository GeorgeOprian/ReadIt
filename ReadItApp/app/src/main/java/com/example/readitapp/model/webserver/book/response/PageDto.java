package com.example.readitapp.model.webserver.book.response;

import java.io.Serializable;
import java.util.List;

public class PageDto<T extends Serializable> implements Serializable {
    private List<T> content;
    private Long offset;

    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalSize;

    public List<T> getContent() {
        return content;
    }

    public Long getOffset() {
        return offset;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Long getTotalSize() {
        return totalSize;
    }
}
