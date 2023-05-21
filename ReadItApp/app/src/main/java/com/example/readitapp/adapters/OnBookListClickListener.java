package com.example.readitapp.adapters;

import com.example.readitapp.model.webserver.book.response.BookListDto;

public interface OnBookListClickListener {

    void onBookClick(BookListDto item);

}
