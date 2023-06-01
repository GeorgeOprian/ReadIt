package com.example.readitapp.model.webserver.book.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class BookRentRequestDto extends BookUserRequestDto {

    @NotNull
    @SerializedName("returnDate")
    @Expose
    private String returnDate;

    @NotNull
    public String  getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(@NotNull String returnDate) {
        this.returnDate = returnDate;
    }
}
