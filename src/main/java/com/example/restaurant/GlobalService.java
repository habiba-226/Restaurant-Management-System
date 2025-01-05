package com.example.restaurant;

public class GlobalService {
    private static int totalOrders = 0;
    private static double totalIncome = 0.0;

    public static int getTotalOrders() {
        return totalOrders;
    }

    public static void incrementTotalOrders(int count) {
        totalOrders += count;
    }

    public static double getTotalIncome() {
        return totalIncome;
    }

    public static void addToTotalIncome(double amount) {
        totalIncome += amount;
    }
}
