package com.example.restaurant;

public class SpicyDecorator extends MenuItem {
    private MenuItem menuItem;

    public SpicyDecorator(MenuItem menuItem) {
        super(menuItem.quantity);  // Decorator will carry over the quantity
        this.menuItem = menuItem;
    }

    @Override
    public double calculateTotalPrice() {
        return menuItem.calculateTotalPrice() + 2.0 * menuItem.quantity;  // £2 per item
    }
}
