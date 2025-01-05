package com.example.restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Menu implements MenuOperations {

    @FXML
    private ListView<String> menuListView;

    @FXML
    private TextField itemNameField;

    @FXML
    private TextField itemPriceField;

    private final List<String> menuItems = new ArrayList<>();

    @Override
    public void displayMenu() {
        menuListView.getItems().setAll(menuItems);
    }

    @Override
    public void addMenuItem(String itemName, double price) {
        String menuItem = itemName + " - $" + price;
        menuItems.add(menuItem);
        displayMenu();
    }

    @Override
    public void removeMenuItem(String itemName) {
        menuItems.removeIf(item -> item.startsWith(itemName));
        displayMenu();
    }

    @FXML
    private void onAddItem() {
        String itemName = itemNameField.getText();
        String priceText = itemPriceField.getText();
        try {
            double price = Double.parseDouble(priceText);
            addMenuItem(itemName, price);

            // Add item to the database
            addItemToDatabase(itemName, price);

            // Clear fields after successful addition
            itemNameField.clear();
            itemPriceField.clear();
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid price. Please enter a valid number.");
        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }

    private void addItemToDatabase(String itemName, double price) throws SQLException {
        String insertQuery = "INSERT INTO menu_item (item_name, price) VALUES (?, ?)";

        try (Connection connection = database.connectDb();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, itemName);
            preparedStatement.setDouble(2, price);
            preparedStatement.executeUpdate();
        }
    }

    @FXML
    private void onRemoveItem() {
        String itemName = itemNameField.getText();
        if (itemName.isEmpty()) {
            showAlert("Error", "Item name cannot be empty.");
            return;
        }

        try {
            // Remove the item from the internal list and ListView
            removeMenuItem(itemName);

            // Remove the item from the database
            removeItemFromDatabase(itemName);

            // Clear the input field
            itemNameField.clear();

        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }

    private void removeItemFromDatabase(String itemName) throws SQLException {
        String deleteQuery = "DELETE FROM menu_item WHERE item_name = ?";

        try (Connection connection = database.connectDb();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, itemName);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                showAlert("Info", "No item found with the name \"" + itemName + "\".");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onBackButtonClick(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeEmployee.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
