package com.example.readitapp.adapters;

import com.example.readitapp.model.webserver.book.response.BookRentResponseDto;

public interface OnMyBooksCLickListener {

    void onBookClick(BookRentResponseDto item);
}
