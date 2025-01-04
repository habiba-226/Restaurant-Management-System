package com.example.restaurant;

public abstract class MenuItemDecorator extends MenuItem {
    protected MenuItem menuItem;

    public MenuItemDecorator(MenuItem menuItem) {
        super(menuItem.getName(), menuItem.getPrice(), menuItem.getQuantity());
        this.menuItem = menuItem;
    }

    @Override
    public double calculateTotalPrice() {
        return menuItem.calculateTotalPrice();
    }

    public int getQuantity() {
        return menuItem.getQuantity();
    }
}
