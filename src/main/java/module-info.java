module com.se206.g11 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.opencsv; //Could be removed in favor of our own implementation
    opens com.se206.g11 to javafx.fxml;
    opens com.se206.g11.screens to javafx.fxml;
    exports com.se206.g11;
    exports com.se206.g11.models;
    exports com.se206.g11.screens;
}