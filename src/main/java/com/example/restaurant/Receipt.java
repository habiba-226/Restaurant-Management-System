package com.example.restaurant;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private String receiptId;
    private List<String> items;
    private double totalPrice;

    public Receipt(String receiptId, String orderDetails, double totalPrice) {
        this.receiptId = receiptId;
        this.totalPrice = totalPrice;
        this.items = new ArrayList<>();
        for (String line : orderDetails.split("\n")) {
            items.add(line);
        }
    }

    public String getReceiptDetails() {
        return String.join("\n", items) + "\nTotal: £" + String.format("%.2f", totalPrice);
    }

    public String getReceiptId() {
        return receiptId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
