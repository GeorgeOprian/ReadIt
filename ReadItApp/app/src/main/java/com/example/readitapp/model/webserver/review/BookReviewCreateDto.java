package com.example.readitapp.model.webserver.review;

import com.example.readitapp.model.webserver.book.request.BookUserRequestDto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookReviewCreateDto extends BookUserRequestDto {

    @SerializedName("nbrStars")
    @Expose
    private Integer nbrStars;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("reviewDate")
    @Expose
    private String reviewDate;

    public Integer getNbrStars() {
        return nbrStars;
    }

    public void setNbrStars(Integer nbrStars) {
        this.nbrStars = nbrStars;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }
}
