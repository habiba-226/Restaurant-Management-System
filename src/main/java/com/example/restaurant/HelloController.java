package com.example.restaurant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginbtn;
    @FXML
    private TextField usernameSignUp;
    @FXML
    private PasswordField passwordSignUp;
    @FXML
    private PasswordField confirmPassword;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void login() {
        String sql = "SELECT * FROM name WHERE username = ? AND password = ?";
        connect = database.connectDb(); // Ensure connectDb() handles connection errors properly

        try {
            // Validate input fields
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill all blank fields.");
                return;
            }

            // Prepare the statement
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());

            // Execute the query
            result = prepare.executeQuery();

            if (result.next()) {
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Successfully Logged In!");

                // Load the next scene
                Parent fxmloader = FXMLLoader.load(getClass().getResource("nextScene.fxml")); // Update with actual FXML
                Stage stage = new Stage();
                Scene scene2 = new Scene(fxmloader);
                stage.setScene(scene2);
                stage.show();

                // Close current stage
                Stage currentStage = (Stage) loginbtn.getScene().getWindow();
                currentStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Wrong Username/Password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close database resources
            closeDatabaseResources();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeDatabaseResources() {
        try {
            if (result != null) result.close();
            if (prepare != null) prepare.close();
            if (connect != null) connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void signUp() {
        // SQL query to insert a new user
        String sql = "INSERT INTO name (username, password) VALUES (?, ?)";
        connect = database.connectDb(); // Ensure this method handles connection errors properly

        try {
            // Validate input fields
            if (usernameSignUp.getText().isEmpty() || passwordSignUp.getText().isEmpty() || confirmPassword.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill all blank fields.");
                return;
            }

            // Check if passwords match
            if (!passwordSignUp.getText().equals(confirmPassword.getText())) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Passwords do not match.");
                return;
            }

            // Prepare SQL statement
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, usernameSignUp.getText());
            prepare.setString(2, passwordSignUp.getText());

            // Execute SQL query
            int rowsInserted = prepare.executeUpdate();
            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Account successfully created!");
                clearSignUpFields(); // Clear the fields after successful sign-up
            } else {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Sign Up failed. Try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred during sign-up.");
        } finally {
            // Close database resources
            closeDatabaseResources();
        }
    }

    private void clearSignUpFields() {
        usernameSignUp.clear();
        passwordSignUp.clear();
        confirmPassword.clear();
    }
}
