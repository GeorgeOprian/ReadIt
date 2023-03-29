package com.example.readitapp.model.webserver;

import com.example.readitapp.model.googlebooks.Item;

public class InputBookModel  {

    private Item item;
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
