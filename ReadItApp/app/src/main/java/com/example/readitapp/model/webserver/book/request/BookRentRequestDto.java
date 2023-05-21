package com.example.readitapp.model.webserver.book.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public class BookRentRequestDto {

    @NotNull
    @SerializedName("bookId")
    @Expose
    private Integer bookId;

    @NotNull
    @SerializedName("userEmail")
    @Expose
    private String userEmail;

    @NotNull
    @SerializedName("returnDate")
    @Expose
    private String returnDate;

    @NotNull
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(@NotNull Integer bookId) {
        this.bookId = bookId;
    }

    @NotNull
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@NotNull String userEmail) {
        this.userEmail = userEmail;
    }

    @NotNull
    public String  getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(@NotNull String returnDate) {
        this.returnDate = returnDate;
    }
}
