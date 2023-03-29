package com.dis.readit.dtos.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SaleInfo implements Serializable {

	@JsonProperty("country")
	private String country;
	@JsonProperty("isEbook")
	private Boolean isEbook;
	@JsonProperty("listPrice")
	private ListPrice listPrice;
	@JsonProperty("buyLink")
	private String buyLink;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getIsEbook() {
		return isEbook;
	}

	public void setIsEbook(Boolean isEbook) {
		this.isEbook = isEbook;
	}

	public ListPrice getListPrice() {
		return listPrice;
	}

	public void setListPrice(ListPrice listPrice) {
		this.listPrice = listPrice;
	}

	public String getBuyLink() {
		return buyLink;
	}

	public void setBuyLink(String buyLink) {
		this.buyLink = buyLink;
	}

}
