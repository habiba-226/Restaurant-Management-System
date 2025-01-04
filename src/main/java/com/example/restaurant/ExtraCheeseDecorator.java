package com.example.restaurant;

public class ExtraCheeseDecorator extends MenuItem {
    private MenuItem menuItem;

    public ExtraCheeseDecorator(MenuItem menuItem) {
        super(menuItem.quantity);  // Decorator will carry over the quantity
        this.menuItem = menuItem;
    }

    @Override
    public double calculateTotalPrice() {
        return menuItem.calculateTotalPrice() + 5.0 * menuItem.quantity;  // £5 per item
    }
}
