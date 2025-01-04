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

import java.util.ArrayList;
import java.util.List;

public class Menu implements MenuOperations {

    @FXML
    private ListView<String> menuListView; // ListView to display menu items

    @FXML
    private TextField itemNameField; // TextField for item name input

    @FXML
    private TextField itemPriceField; // TextField for item price input

    private final List<String> menuItems = new ArrayList<>(); // Internal list to hold menu items

    @Override
    public void displayMenu() {
        menuListView.getItems().setAll(menuItems); // Update ListView with menu items
    }

    @Override
    public void addMenuItem(String itemName, double price) {
        String menuItem = itemName + " - $" + price;
        menuItems.add(menuItem);
        displayMenu(); // Refresh menu display
    }

    @Override
    public void removeMenuItem(String itemName) {
        menuItems.removeIf(item -> item.startsWith(itemName));
        displayMenu(); // Refresh menu display
    }

    // Event handler to add an item
    @FXML
    private void onAddItem() {
        String itemName = itemNameField.getText();
        String priceText = itemPriceField.getText();
        try {
            double price = Double.parseDouble(priceText);
            addMenuItem(itemName, price); // Use the interface method
            itemNameField.clear();
            itemPriceField.clear();
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid price. Please enter a valid number.");
        }
    }

    // Event handler to remove an item
    @FXML
    private void onRemoveItem() {
        String itemName = itemNameField.getText();
        removeMenuItem(itemName); // Use the interface method
        itemNameField.clear();
    }

    // Utility method to display alerts
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
            // Access the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Close the current stage
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