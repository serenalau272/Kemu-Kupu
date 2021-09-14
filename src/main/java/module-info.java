module com.se206.g11 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    opens com.se206.g11 to javafx.fxml;
    exports com.se206.g11;
}