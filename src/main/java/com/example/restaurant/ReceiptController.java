package com.example.restaurant;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ReceiptController {
    @FXML
    private Label receiptIdLabel;
    @FXML
    private TextArea orderDetailsTextArea;
    @FXML
    private Label totalCostLabel;

    public void initialize(String receiptId, String orderDetails, double totalCost) {
        receiptIdLabel.setText("Receipt ID: " + receiptId);
        orderDetailsTextArea.setText(orderDetails);
        totalCostLabel.setText("Total: £" + String.format("%.2f", totalCost));
    }

    @FXML
    private void closeReceipt() {
        Stage stage = (Stage) receiptIdLabel.getScene().getWindow();
        stage.close();
    }
}

