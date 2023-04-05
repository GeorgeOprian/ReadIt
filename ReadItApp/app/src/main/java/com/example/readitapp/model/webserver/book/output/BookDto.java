package com.example.readitapp.model.webserver.book.output;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BookDto implements Serializable {

    @SerializedName("bookId")
    @Expose
    private Integer bookId;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName( "author")
    @Expose
    private String author;

    @SerializedName( "publisher")
    @Expose
    private String publisher;

    @SerializedName( "publishedDate")
    @Expose
    private String publishedDate;
    @SerializedName( "description")
    @Expose
    private String description;

    @SerializedName( "isbn")
    @Expose
    private String isbn; //industryIdentifiers[0]

    @SerializedName( "pageCount")
    @Expose
    private Integer pageCount;
    @SerializedName( "averageRating")
    @Expose
    private Double averageRating;
    @SerializedName( "ratingsCount")
    @Expose
    private Integer ratingsCount;
    @SerializedName( "maturityRating")
    @Expose
    private String maturityRating;

    @SerializedName( "language")
    @Expose
    private String language;

    @SerializedName( "inStock")
    @Expose
    private Integer inStock;

    // TODO gop 01.04.2023: vezi userMapper
    @SerializedName("thumbnail")
    @Expose
    private ThumbnailDto thumbnail;

    @SerializedName("categories")
    @Expose
    private List<CategoryDto> categories;

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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
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

    public String getMaturityRating() {
        return maturityRating;
    }

    public void setMaturityRating(String maturityRating) {
        this.maturityRating = maturityRating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
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
}