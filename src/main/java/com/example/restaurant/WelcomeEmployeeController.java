package com.example.restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class WelcomeEmployeeController {

    @FXML
    private Label employeeNameLabel; // To display the employee's name

    @FXML
    private Label employeeRoleLabel; // To display the employee's role

    @FXML
    private ListView<EmployeeWithaRole> employeeListView; // ListView for colleagues' names

    @FXML
    private Button viewRoleButton; // Button to view the colleague's role
    @FXML
    private Button backkButton;
    @FXML
    private Button dashbutton;

    private String employeeId;
    private String employeeName;

    // Method to set employee data
    public void setEmployeeData(String id, String name) {
        this.employeeId = id;
        this.employeeName = name;

        // Display employee's name
        employeeNameLabel.setText("Welcome, " + employeeName);

        // Fetch and display the employee's role
        displayEmployeeRole();

        // Load the colleagues' names in the ListView
        loadColleaguesList();
    }

    // Fetch the role of the employee and display it
    private void displayEmployeeRole() {
        String role = getRoleFromDatabase(employeeId);
        employeeRoleLabel.setText("Your Role: " + role);
    }

    // Method to get the employee role from the database
    private String getRoleFromDatabase(String employeeId) {
        String role = "Role not found"; // Default role if not found
        String sql = "SELECT Role FROM employee WHERE Employee_ID = ?";
        try (Connection connect = database.connectDb();
             PreparedStatement prepare = connect.prepareStatement(sql)) {

            prepare.setString(1, employeeId);
            try (ResultSet result = prepare.executeQuery()) {
                if (result.next()) {
                    role = result.getString("Role"); // Fetch role
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }

    // Load the colleagues' names into the ListView
    private void loadColleaguesList() {
        List<EmployeeWithaRole> colleagues = new ArrayList<>();
        String sql = "SELECT Employee_ID, Ename, Role FROM employee WHERE Employee_ID != ?"; // Exclude current employee

        try (Connection connect = database.connectDb();
             PreparedStatement prepare = connect.prepareStatement(sql)) {

            prepare.setString(1, employeeId);
            try (ResultSet result = prepare.executeQuery()) {
                while (result.next()) {
                    String id = result.getString("Employee_ID");
                    String name = result.getString("Ename");
                    String role = result.getString("Role");

                    // Create an EmployeeWithaRole object and add it to the list
                    colleagues.add(new EmployeeWithaRole(id, name, role));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set the ListView items to the list of EmployeeWithRole objects
        employeeListView.setItems(javafx.collections.FXCollections.observableArrayList(colleagues));
    }

    // Button to navigate to the colleague's role scene
    @FXML
    public void viewColleagueRole(ActionEvent event) {
        EmployeeWithaRole selectedColleague = employeeListView.getSelectionModel().getSelectedItem();
        if (selectedColleague != null) {
            // Get the colleague's ID
            String colleagueId = selectedColleague.getEmployeeId();

            try {
                // Load the scene for viewing the colleague's role
                FXMLLoader loader = new FXMLLoader(getClass().getResource("viewcolleague.fxml"));
                Parent root = loader.load();

                ViewColleagueRoleController controller = loader.getController();
                controller.setColleagueId(colleagueId); // Set the colleague's ID to the controller

                // Navigate to the new scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the colleague's role view.");
            }
        } else {
            showAlert("Please select a colleague."); // Using overloaded method
        }
    }

    // Overloaded showAlert methods
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String message) {
        showAlert(Alert.AlertType.INFORMATION, "Information", message);
    }

    private void showAlert(String title, String message) {
        showAlert(Alert.AlertType.WARNING, title, message);
    }


    public void onBackButtonClick(ActionEvent event) {
        try {
            // Go back to the previous scene
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

    public void DashBoardButtonClick(ActionEvent event) {
        try {
            // Load the Customer Scene FXML file
            Parent root = FXMLLoader.load(getClass().getResource("DashBoardEmployee.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Customer DashBoard"); // Optional: Set the stage title
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error Message", "Unable to load the Employee Dashboard Scene.");
        }
    }
    public void ChangeMenu(ActionEvent event) {
        try {
            // Load the Customer Scene FXML file
            Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Change Menu"); // Optional: Set the stage title
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error Message", "Unable to load the Menu Scene.");
        }
    }
}



