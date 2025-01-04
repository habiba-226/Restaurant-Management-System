package com.example.restaurant;

public interface MenuOperations {
    void displayMenu();
    void addMenuItem(String itemName, double price);
    void removeMenuItem(String itemName);
}
