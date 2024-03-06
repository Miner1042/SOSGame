module com.example.sosgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sosgame to javafx.fxml;
    exports com.example.sosgame;
}