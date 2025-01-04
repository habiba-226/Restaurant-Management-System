package com.example.restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class nextScene { // Renamed class to follow Java naming conventions
    @FXML
    private Button goback2;

    // Navigate to Employee Scene
    @FXML
    private void goToEmployeeScene(ActionEvent event) {
        loadScene(event, "checkemployeeID.fxml", "Employee Scene");
    }

    // Navigate to Customer Scene
    @FXML
    private void goToCustomerScene(ActionEvent event) {
        loadScene(event, "customerScene.fxml", "Customer Dashboard");
    }

    // Go back to the previous scene
    @FXML
    public void onBackButtonClick(ActionEvent event) {
        loadScene(event, "hello-view.fxml", "Welcome");
    }

    // Utility method to load scenes
    private void loadScene(ActionEvent event, String fxmlFile, String stageTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(stageTitle);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the scene: " + fxmlFile);
        }
    }

    // Utility method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
