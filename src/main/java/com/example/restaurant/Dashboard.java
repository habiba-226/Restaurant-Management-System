package com.example.restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Dashboard {

    @FXML
    private Label customersLabel;
    @FXML
    private Label incomeLabel;
    @FXML
    private BarChart<String, Number> numberOfOrdersChart;
    @FXML
    private BarChart<String, Number> numberOfOrdersChart1;

    public void initialize() {
        updateDashboard();
    }

    private void updateDashboard() {
        customersLabel.setText(String.valueOf(GlobalService.getTotalOrders()));
        incomeLabel.setText("£" + String.format("%.2f", GlobalService.getTotalIncome()));
    }

    @FXML
    public void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeEmployee.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the previous scene.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
