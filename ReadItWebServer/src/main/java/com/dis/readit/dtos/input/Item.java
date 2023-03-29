package com.dis.readit.dtos.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Item implements Serializable {

	@JsonProperty("id")
	private String id;
	@JsonProperty("volumeInfo")
	private VolumeInfo volumeInfo;
	@JsonProperty("saleInfo")
	private SaleInfo saleInfo;
	@JsonProperty("searchInfo")
	private SearchInfo searchInfo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public VolumeInfo getVolumeInfo() {
		return volumeInfo;
	}

	public void setVolumeInfo(VolumeInfo volumeInfo) {
		this.volumeInfo = volumeInfo;
	}

	public SaleInfo getSaleInfo() {
		return saleInfo;
	}

	public void setSaleInfo(SaleInfo saleInfo) {
		this.saleInfo = saleInfo;
	}

	public SearchInfo getSearchInfo() {
		return searchInfo;
	}

	public void setSearchInfo(SearchInfo searchInfo) {
		this.searchInfo = searchInfo;
	}

}
