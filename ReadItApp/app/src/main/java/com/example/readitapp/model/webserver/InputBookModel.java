package com.example.readitapp.model.webserver;

import com.example.readitapp.model.googlebooks.Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InputBookModel  {

    @SerializedName("item")
    @Expose
    private Item item;

    @SerializedName("inStock")
    @Expose
    private Integer inStock;

    public InputBookModel() {
    }

    public InputBookModel(Item item, Integer inStock) {
        this.item = item;
        this.inStock = inStock;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }
}
