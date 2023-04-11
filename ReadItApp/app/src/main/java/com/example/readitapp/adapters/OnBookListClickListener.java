package com.example.readitapp.adapters;

import com.example.readitapp.model.webserver.book.input.BookDto;
import com.example.readitapp.model.webserver.book.input.BookListDto;

public interface OnBookListClickListener {

    void onBookClick(BookListDto item);

}
