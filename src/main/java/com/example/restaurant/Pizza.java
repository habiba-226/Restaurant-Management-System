package com.example.restaurant;

public class Pizza extends MenuItem {

    public Pizza(int quantity) {
        super(quantity);
        this.price = 10.0;
    }

    @Override
    public double calculateTotalPrice() {
        return this.price * this.quantity;
    }
}

