package com.dis.readit.dtos.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class VolumeInfo implements Serializable {

	@JsonProperty("title")
	private String title;
	@JsonProperty("subtitle")
	private String subtitle;
	@JsonProperty("authors")
	private List<String> authors;
	@JsonProperty("publisher")
	private String publisher;
	@JsonProperty("publishedDate")
	private String publishedDate;
	@JsonProperty("description")
	private String description;
	@JsonProperty("industryIdentifiers")
	private List<IndustryIdentifier> industryIdentifiers;
	@JsonProperty("pageCount")
	private Integer pageCount;
	@JsonProperty("categories")
	private List<String> categories;
	@JsonProperty("averageRating")
	private Double averageRating;
	@JsonProperty("ratingsCount")
	private Integer ratingsCount;
	@JsonProperty("maturityRating")
	private String maturityRating;
	@JsonProperty("imageLinks")
	private ImageLinks imageLinks;
	@JsonProperty("language")
	private String language;
	@JsonProperty("previewLink")
	private String previewLink;
	@JsonProperty("infoLink")
	private String infoLink;
	@JsonProperty("canonicalVolumeLink")
	private String canonicalVolumeLink;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
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

	public List<IndustryIdentifier> getIndustryIdentifiers() {
		return industryIdentifiers;
	}

	public void setIndustryIdentifiers(List<IndustryIdentifier> industryIdentifiers) {
		this.industryIdentifiers = industryIdentifiers;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
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

	public ImageLinks getImageLinks() {
		return imageLinks;
	}

	public void setImageLinks(ImageLinks imageLinks) {
		this.imageLinks = imageLinks;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPreviewLink() {
		return previewLink;
	}

	public void setPreviewLink(String previewLink) {
		this.previewLink = previewLink;
	}

	public String getInfoLink() {
		return infoLink;
	}

	public void setInfoLink(String infoLink) {
		this.infoLink = infoLink;
	}

	public String getCanonicalVolumeLink() {
		return canonicalVolumeLink;
	}

	public void setCanonicalVolumeLink(String canonicalVolumeLink) {
		this.canonicalVolumeLink = canonicalVolumeLink;
	}

}