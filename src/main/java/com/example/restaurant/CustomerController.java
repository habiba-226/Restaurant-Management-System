package com.example.restaurant;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerController {
    @FXML
    private TextField searchTextField;

    // Pizza Components
    @FXML
    private Label pizzaPriceLabel, pizzaAvailableLabel;
    @FXML
    private TextField pizzaQuantityField;
    @FXML
    private Button pizzaAddButton;

    // Pasta Components
    @FXML
    private Label pastaPriceLabel, pastaAvailableLabel;
    @FXML
    private TextField pastaQuantityField;
    @FXML
    private Button pastaAddButton;

    // Juice Components
    @FXML
    private Label juicePriceLabel, juiceAvailableLabel;
    @FXML
    private TextField juiceQuantityField;
    @FXML
    private Button juiceAddButton;

    // Sushi Components
    @FXML
    private Label sushiPriceLabel, sushiAvailableLabel;
    @FXML
    private TextField sushiQuantityField;
    @FXML
    private Button sushiAddButton;

    // Sandwich Components
    @FXML
    private Label sandwichPriceLabel, sandwichAvailableLabel;
    @FXML
    private TextField sandwichQuantityField;
    @FXML
    private Button sandwichAddButton;

    // Burger Components
    @FXML
    private Label burgerPriceLabel, burgerAvailableLabel;
    @FXML
    private TextField burgerQuantityField;
    @FXML
    private Button burgerAddButton;

    @FXML
    private Button placeOrderButton;

    @FXML
    private Button resetButton;
    // Total Cost
    @FXML
    private Label totalCostLabel;

    private StringBuilder orderDetails = new StringBuilder();

    private double totalCost = 0.0;

    @FXML
    public void initialize() {
        // Add button handlers
        pizzaAddButton.setOnAction(event -> handleAddItem("Pizza", pizzaPriceLabel, pizzaAvailableLabel, pizzaQuantityField));
        pastaAddButton.setOnAction(event -> handleAddItem("Pasta", pastaPriceLabel, pastaAvailableLabel, pastaQuantityField));
        juiceAddButton.setOnAction(event -> handleAddItem("Juice", juicePriceLabel, juiceAvailableLabel, juiceQuantityField));
        sushiAddButton.setOnAction(event -> handleAddItem("Sushi", sushiPriceLabel, sushiAvailableLabel, sushiQuantityField));
        sandwichAddButton.setOnAction(event -> handleAddItem("Sandwich", sandwichPriceLabel, sandwichAvailableLabel, sandwichQuantityField));
        burgerAddButton.setOnAction(event -> handleAddItem("Burger", burgerPriceLabel, burgerAvailableLabel, burgerQuantityField));
        placeOrderButton.setOnAction(event -> placeOrder());
        resetButton.setOnAction(e -> resetFields());
    }

    public double getTotalCost() {
        return totalCost;
    }

    private void handleAddItem(String itemName, Label priceLabel, Label availableLabel, TextField quantityField) {
        try {
            // Get price and available quantity
            double price = Double.parseDouble(priceLabel.getText().substring(1));
            int available = Integer.parseInt(availableLabel.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            if (quantity <= 0) {
                showAlert("Invalid Quantity", "Quantity must be greater than zero.");
                return;
            }

            if (quantity > available) {
                showAlert("Insufficient Stock", "Not enough items available.");
                return;
            }

            totalCost += price * quantity;
            totalCostLabel.setText("£" + String.format("%.2f", totalCost));

            available -= quantity;
            availableLabel.setText(String.valueOf(available));

            quantityField.clear();
            orderDetails.append(String.format("%d x %s @ £%.2f each\n", quantity, itemName, price));

            System.out.println("Added " + quantity + " of " + itemName + " to the order.");

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid quantity.");
        }
    }

    private void resetFields() {
        // Reset item quantities to 0
        pizzaQuantityField.clear();
        pastaQuantityField.clear();
        sandwichQuantityField.clear();
        sushiQuantityField.clear();
        juiceQuantityField.clear();
        burgerQuantityField.clear();

        // Reset the available stock back to initial values
        int pizzaAvailable = 15;
        int pastaAvailable = 10;
        int burgerAvailable = 10;
        int sushiAvailable = 12;
        int juiceAvailable = 20;
        int sandwichAvailable = 8;

        pizzaAvailableLabel.setText(String.valueOf(pizzaAvailable));
        pastaAvailableLabel.setText(String.valueOf(pastaAvailable));
        burgerAvailableLabel.setText(String.valueOf(burgerAvailable));
        sushiAvailableLabel.setText(String.valueOf(sushiAvailable));
        juiceAvailableLabel.setText(String.valueOf(juiceAvailable));
        sandwichAvailableLabel.setText(String.valueOf(sandwichAvailable));

        totalCost = 0.0;
        totalCostLabel.setText("0.00");
    }

    private void placeOrder() {
        if (orderDetails.length() == 0) {
            showAlert("No Items", "You must add at least one item to place an order.");
            return;
        }

        String receiptId = "R" + System.currentTimeMillis();
        Receipt receipt = new Receipt(receiptId, orderDetails.toString(), getTotalCost());

        try {
            // Load the receipt FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("receipt.fxml"));
            Parent root = loader.load();

            // Get the controller and initialize it
            ReceiptController controller = loader.getController();
            controller.initialize(receiptId, orderDetails.toString(), getTotalCost());

            // Create a new scene and show the receipt
            Scene receiptScene = new Scene(root);
            Stage receiptStage = new Stage();
            receiptStage.setScene(receiptScene);
            receiptStage.setTitle("Order Receipt");
            receiptStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


