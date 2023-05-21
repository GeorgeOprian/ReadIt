package com.example.readitapp.model.webserver.book.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookDto extends BookListDto implements Serializable {

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

    @SerializedName( "maturityRating")
    @Expose
    private String maturityRating;

    @SerializedName( "language")
    @Expose
    private String language;

    @SerializedName( "inStock")
    @Expose
    private Integer inStock;

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
}
