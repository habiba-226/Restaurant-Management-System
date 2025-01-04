package com.example.restaurant;
import java.sql.Connection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class checkEmployeeId {
    @FXML
    private TextField employeeIdField;
    @FXML
    private Button goback3;

    public void checksemployeeid(ActionEvent event) {
        String sql = "SELECT * FROM employee WHERE Employee_ID = ?";
        Connection connect = null;
        PreparedStatement prepare = null;
        ResultSet result = null;

        try {
            // Validate input field
            if (employeeIdField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Please enter your Employee ID.");
                return;
            }

            // Connect to the database
            connect = database.connectDb();
            if (connect == null) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Database connection failed.");
                return;
            }

            // Prepare SQL statement
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, Integer.parseInt(employeeIdField.getText()));

            // Execute the query
            result = prepare.executeQuery();

            if (result.next()) {
                // Access granted
                String employeeId = result.getString("Employee_ID");
                String employeeName = result.getString("Ename");

                // Pass the employee data to the WelcomeEmployeeController
                FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeEmployee.fxml"));
                Parent root = loader.load();

                WelcomeEmployeeController controller = loader.getController();
                controller.setEmployeeData(employeeId, employeeName); // Set the employee ID and name in the controller

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                // Invalid Employee ID
                showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid Employee ID.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please enter a valid numeric Employee ID.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred: " + e.getMessage());
        } finally {
            // Close database resources
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Utility method to display alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
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

            // Optionally, you could load the previous scene and set it to the stage again
            FXMLLoader loader = new FXMLLoader(getClass().getResource("nextScene.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
