package com.example.restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewColleagueRoleController {

    @FXML
    private Label colleagueNameLabel;  // Label to display colleague's name

    @FXML
    private Label colleagueRoleLabel;  // Label to display colleague's role

    @FXML
    private ImageView colleagueImageView;  // ImageView to show colleague's image

    @FXML
    private Button backButton;  // Button to go back

    private String colleagueId;

    // Method to set the colleague's ID and display the corresponding role, name, and image
    public void setColleagueId(String colleagueId) {
        this.colleagueId = colleagueId;

        // Fetch the colleague's name and role from the database
        Colleague colleague = getColleagueDetailsFromDatabase(colleagueId);

        // Set colleague's name and role
        colleagueNameLabel.setText("Colleague's Name: " + colleague.getName());
        colleagueRoleLabel.setText("Colleague's Role: " + colleague.getRole());

        // Set the image based on the colleague's ID
        setColleagueImage(colleagueId);
    }

    // Fetch colleague's details (name and role) from the database
    private Colleague getColleagueDetailsFromDatabase(String colleagueId) {
        String name = "Name not found";  // Default name if not found
        String role = "Role not found";  // Default role if not found

        String sql = "SELECT Ename, Role FROM employee WHERE Employee_ID = ?";

        try (Connection connect = database.connectDb();
             PreparedStatement prepare = connect.prepareStatement(sql)) {

            prepare.setString(1, colleagueId);
            try (ResultSet result = prepare.executeQuery()) {
                if (result.next()) {
                    name = result.getString("Ename");  // Fetch colleague name
                    role = result.getString("Role");  // Fetch colleague role
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Colleague(name, role);
    }

    private void setColleagueImage(String colleagueId) {
        // Assuming you have a naming convention like "colleague1.jpg", "colleague2.jpg", etc.
        String imagePath = "/images/colleague" + colleagueId + ".jpg";  // Example naming pattern

        // Load image from the resources folder
        Image image = new Image(getClass().getResourceAsStream(imagePath));

        // Check if the image was loaded successfully
        if (image.isError()) {
            System.out.println("Error loading image for colleague ID: " + colleagueId);
        }

        // Set the image in the ImageView
        colleagueImageView.setImage(image);
    }


    // Handle back button click (Go back to the previous scene)
    @FXML
    public void onBackButtonClick(ActionEvent event) {
        try {
            // Access the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Close the current stage
            stage.close();

            // Optionally, you could load the previous scene and set it to the stage again
            FXMLLoader loader = new FXMLLoader(getClass().getResource("checkEmployeeId.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


