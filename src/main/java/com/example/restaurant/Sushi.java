package com.example.restaurant;

public class Sushi extends MenuItem {

    public Sushi(int quantity) {
        super(quantity);
        this.price = 50.0;
    }

    @Override
    public double calculateTotalPrice() {
        return this.price * this.quantity;
    }
}
