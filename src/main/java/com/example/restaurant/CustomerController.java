package com.example.restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

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


        // Sushi Components
        @FXML
        private Label sushiPriceLabel, sushiAvailableLabel;
        @FXML
        private TextField sushiQuantityField;
        @FXML
        private Button sushiAddButton;


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

        @FXML
        private Button backButton;

        @FXML
        private Label totalCostLabel;

        private StringBuilder orderDetails = new StringBuilder();

        private double totalCost = 0.0;
        @FXML
        private CheckBox pizzaSpicyCheck;

        @FXML
        private CheckBox pizzaExtraCheeseCheck;

        @FXML
        private CheckBox pastaSpicyCheck;

        @FXML
        private CheckBox pastaExtraCheeseCheck;

        @FXML
        private CheckBox sushiSpicyCheck;

        @FXML
        private CheckBox sushiExtraCheeseCheck;

        @FXML
        private CheckBox burgerSpicyCheck;

        @FXML
        private CheckBox burgerExtraCheeseCheck;

        @FXML
        public void initialize() {
            // Add button handlers
            pizzaAddButton.setOnAction(event -> handleAddItem("Pizza", pizzaPriceLabel, pizzaAvailableLabel, pizzaQuantityField, pizzaSpicyCheck, pizzaExtraCheeseCheck));
            pastaAddButton.setOnAction(event -> handleAddItem("Pasta", pastaPriceLabel, pastaAvailableLabel, pastaQuantityField, pastaSpicyCheck, pastaExtraCheeseCheck));
            sushiAddButton.setOnAction(event -> handleAddItem("Sushi", sushiPriceLabel, sushiAvailableLabel, sushiQuantityField, sushiSpicyCheck, sushiExtraCheeseCheck));
            burgerAddButton.setOnAction(event -> handleAddItem("Burger", burgerPriceLabel, burgerAvailableLabel, burgerQuantityField, burgerSpicyCheck, burgerExtraCheeseCheck));
            placeOrderButton.setOnAction(event -> placeOrder());
            resetButton.setOnAction(e -> resetFields());
            backButton.setOnAction(e -> back(e));
        }

        public double getTotalCost() {
            return totalCost;
        }

        public void handleAddItem(String itemName, Label priceLabel, Label availableLabel, TextField quantityField,
                                  CheckBox spicyCheck, CheckBox extraCheeseCheck) {
            try {
                // Get the base price and available quantity
                double price = Double.parseDouble(priceLabel.getText().substring(1));
                int available = Integer.parseInt(availableLabel.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                // Validate quantity
                if (quantity <= 0) {
                    showAlert("Invalid Quantity", "Quantity must be greater than zero.");
                    return;
                }

                if (quantity > available) {
                    showAlert("Insufficient Stock", "Not enough items available.");
                    return;
                }

                MenuItem menuItem = null;
                if (itemName.equals("Pizza")) {
                    menuItem = new Pizza(quantity);
                } else if (itemName.equals("Pasta")) {
                    menuItem = new Pasta(quantity);
                } else if (itemName.equals("Sushi")) {
                    menuItem = new Sushi(quantity);
                } else if (itemName.equals("Burger")) {
                    menuItem = new Burger(quantity);
                }

                double totalAdditionalPrice = 0;
                if (spicyCheck.isSelected()) {
                    menuItem = new SpicyDecorator(menuItem);
                    totalAdditionalPrice += 2;
                }

                if (extraCheeseCheck.isSelected()) {
                    menuItem = new ExtraCheeseDecorator(menuItem);
                    totalAdditionalPrice += 5;
                }

                double finalPricePerItem = menuItem.calculateTotalPrice() / quantity;

                double totalItemPrice = finalPricePerItem * quantity;

                totalCost += totalItemPrice;
                totalCostLabel.setText("£" + String.format("%.2f", totalCost));

                available -= quantity;
                availableLabel.setText(String.valueOf(available));

                quantityField.clear();

                orderDetails.append(String.format("%d x %s @ £%.2f (added £%.2f each)\n", quantity, itemName, finalPricePerItem,totalAdditionalPrice));

                GlobalService.addToTotalIncome(totalCost);
                GlobalService.incrementTotalOrders(quantity);
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid quantity.");
            }
        }


        private void resetFields() {
            pizzaQuantityField.clear();
            pastaQuantityField.clear();
            sushiQuantityField.clear();
            burgerQuantityField.clear();

            int pizzaAvailable = 15;
            int pastaAvailable = 10;
            int burgerAvailable = 10;
            int sushiAvailable = 12;

            pizzaAvailableLabel.setText(String.valueOf(pizzaAvailable));
            pastaAvailableLabel.setText(String.valueOf(pastaAvailable));
            burgerAvailableLabel.setText(String.valueOf(burgerAvailable));
            sushiAvailableLabel.setText(String.valueOf(sushiAvailable));

            orderDetails.setLength(0);
            totalCost = 0.0;
            totalCostLabel.setText("0.00");
        }

    private void placeOrder() {
        if (orderDetails.length() == 0) {
            showAlert("No Items", "You must add at least one item to place an order.");
            return;
        }

        String receiptId = "R" + System.currentTimeMillis();
        double totalCost = getTotalCost();

        try (Connection connection = database.connectDb()) {
            String insertQuery = "INSERT INTO receipt (receipt_id, total_price) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = ((java.sql.Connection) connection).prepareStatement(insertQuery)) {
                preparedStatement.setString(1, receiptId);
                preparedStatement.setDouble(2, totalCost);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save the receipt in the database.");
            return;
        }

        Receipt receipt = new Receipt(receiptId, orderDetails.toString(), totalCost);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Receipt.fxml"));
            Parent root = loader.load();

            ReceiptController controller = loader.getController();
            controller.initialize(receiptId, orderDetails.toString(), totalCost);

            Scene receiptScene = new Scene(root);
            Stage receiptStage = new Stage();
            receiptStage.setScene(receiptScene);
            receiptStage.setTitle("Order Receipt");
            receiptStage.show();
            resetFields();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the receipt screen.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("nextScene.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the previous scene.");
        }
    }
}


