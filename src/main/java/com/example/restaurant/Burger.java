package com.example.restaurant;

public class Burger extends MenuItem {

    public Burger(int quantity) {
        super(quantity);
        this.price = 12.50;
    }

    @Override
    public double calculateTotalPrice() {
        return this.price * this.quantity;
    }
}
