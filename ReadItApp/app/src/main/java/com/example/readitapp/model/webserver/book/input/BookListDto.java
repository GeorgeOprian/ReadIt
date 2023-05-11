package com.example.readitapp.model.webserver.book.input;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BookListDto implements Serializable {

    @SerializedName("bookId")
    @Expose
    private Integer bookId;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName( "author")
    @Expose
    private String author;

    @SerializedName( "averageRating")
    @Expose
    private Double averageRating;

    @SerializedName( "ratingsCount")
    @Expose
    private Integer ratingsCount;

    @SerializedName("thumbnail")
    @Expose
    private ThumbnailDto thumbnail;

    @SerializedName("categories")
    @Expose
    private List<CategoryDto> categories;

    @SerializedName("inStock")
    @Expose
    private Integer inStock;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(Integer ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public ThumbnailDto getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ThumbnailDto thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }
}