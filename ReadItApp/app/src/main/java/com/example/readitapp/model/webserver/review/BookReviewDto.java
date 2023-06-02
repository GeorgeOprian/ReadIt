package com.example.readitapp.model.webserver.review;

import com.example.readitapp.model.webserver.user.output.UserDto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookReviewDto {

    @SerializedName("user")
    @Expose
    private UserDto user;

    @SerializedName("nbrStars")
    @Expose
    private Integer nbrStars;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("reviewDate")
    @Expose
    private String reviewDate;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

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
