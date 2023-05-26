package com.example.readitapp.model.webserver.book.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookRentResponseDto {

    @SerializedName("returnDate")
    @Expose
    private String returnDate;

    @SerializedName("rentedBook")
    @Expose
    private BookDto book;

    @SerializedName("rentId")
    @Expose
    private Integer rentId;

    @SerializedName("returned")
    @Expose
    private Boolean returned;

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public Integer getRentId() {
        return rentId;
    }

    public void setRentId(Integer rentId) {
        this.rentId = rentId;
    }
}
