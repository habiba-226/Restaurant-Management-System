module com.example.restaurant {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens com.example.restaurant to javafx.fxml;
    exports com.example.restaurant;
}