package com.example.readitapp.model.webserver.book.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class BookUserRequestDto implements Serializable {

    @NotNull
    @SerializedName("bookId")
    @Expose
    private Integer bookId;

    @NotNull
    @SerializedName("userEmail")
    @Expose
    private String userEmail;

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
}
