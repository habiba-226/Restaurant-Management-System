package com.example.restaurant;

public class Pasta extends MenuItem {

    public Pasta(int quantity) {
        super(quantity);
        this.price = 8.0;
    }

    @Override
    public double calculateTotalPrice() {
        return this.price * this.quantity;
    }
}
