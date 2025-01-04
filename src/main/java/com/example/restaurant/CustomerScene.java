package com.example.restaurant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerScene {
    @FXML
    private TextField searchTextField;

    @FXML
    private Label pizzaPriceLabel, pizzaAvailableLabel;
    @FXML
    private TextField pizzaQuantityField;
    @FXML
    private Button pizzaAddButton;

    @FXML
    private Label pastaPriceLabel, pastaAvailableLabel;
    @FXML
    private TextField pastaQuantityField;
    @FXML
    private Button pastaAddButton;

    @FXML
    private Label juicePriceLabel, juiceAvailableLabel;
    @FXML
    private TextField juiceQuantityField;
    @FXML
    private Button juiceAddButton;

    @FXML
    private Label sushiPriceLabel, sushiAvailableLabel;
    @FXML
    private TextField sushiQuantityField;
    @FXML
    private Button sushiAddButton;

    @FXML
    private Label sandwichPriceLabel, sandwichAvailableLabel;
    @FXML
    private TextField sandwichQuantityField;
    @FXML
    private Button sandwichAddButton;

    @FXML
    private Label burgerPriceLabel, burgerAvailableLabel;
    @FXML
    private TextField burgerQuantityField;
    @FXML
    private Button burgerAddButton;

    @FXML
    private Button placeOrderButton, resetButton;

    @FXML
    private Label totalCostLabel;

    private StringBuilder orderDetails = new StringBuilder();
    private double totalCost = 0.0;

    @FXML
    public void initialize() {
        pizzaAddButton.setOnAction(event -> handleAddItem("Pizza", pizzaPriceLabel, pizzaAvailableLabel, pizzaQuantityField));
        pastaAddButton.setOnAction(event -> handleAddItem("Pasta", pastaPriceLabel, pastaAvailableLabel, pastaQuantityField));
        juiceAddButton.setOnAction(event -> handleAddItem("Juice", juicePriceLabel, juiceAvailableLabel, juiceQuantityField));
        sushiAddButton.setOnAction(event -> handleAddItem("Sushi", sushiPriceLabel, sushiAvailableLabel, sushiQuantityField));
        sandwichAddButton.setOnAction(event -> handleAddItem("Sandwich", sandwichPriceLabel, sandwichAvailableLabel, sandwichQuantityField));
        burgerAddButton.setOnAction(event -> handleAddItem("Burger", burgerPriceLabel, burgerAvailableLabel, burgerQuantityField));
        resetButton.setOnAction(e -> resetFields());
    }

    private void handleAddItem(String itemName, Label priceLabel, Label availableLabel, TextField quantityField) {
        try {
            double price = Double.parseDouble(priceLabel.getText().substring(1));
            int available = Integer.parseInt(availableLabel.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            if (quantity <= 0 || quantity > available) {
                showAlert("Invalid Quantity", "Ensure the quantity is valid and available.");
                return;
            }

            totalCost += price * quantity;
            totalCostLabel.setText("£" + String.format("%.2f", totalCost));

            available -= quantity;
            availableLabel.setText(String.valueOf(available));

            quantityField.clear();
            orderDetails.append(String.format("%d x %s @ £%.2f each\n", quantity, itemName, price));
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid quantity.");
        }
    }

    private void resetFields() {
        pizzaAvailableLabel.setText("15");
        pastaAvailableLabel.setText("10");
        burgerAvailableLabel.setText("10");
        sushiAvailableLabel.setText("12");
        juiceAvailableLabel.setText("20");
        sandwichAvailableLabel.setText("8");
        pizzaQuantityField.clear();
        pastaQuantityField.clear();
        burgerQuantityField.clear();
        sushiQuantityField.clear();
        juiceQuantityField.clear();
        sandwichQuantityField.clear();
        totalCost = 0.0;
        totalCostLabel.setText("£0.00");
        orderDetails.setLength(0);
    }

    @FXML
    private void placeOrder() {
        if (orderDetails.length() == 0) {
            showAlert("No Items", "You must add at least one item to place an order.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurant/Receipt.fxml"));
            Parent root = loader.load();

            ReceiptController controller = loader.getController();
            controller.initialize("R" + System.currentTimeMillis(), orderDetails.toString(), totalCost);

            Scene receiptScene = new Scene(root);
            Stage receiptStage = new Stage();
            receiptStage.setScene(receiptScene);
            receiptStage.setTitle("Order Receipt");
            receiptStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load the receipt view.");
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

