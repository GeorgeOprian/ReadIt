package com.example.readitapp.model.webserver.book.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryDto implements Serializable {

    @SerializedName("categoryName")
    @Expose
    private String categoryName;

    public CategoryDto() {
    }

    public CategoryDto(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}