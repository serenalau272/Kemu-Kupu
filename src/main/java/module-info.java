module com.se206.g11 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires transitive javafx.graphics;
    opens com.se206.g11 to javafx.fxml;
    opens com.se206.g11.controllers to javafx.fxml;
    opens com.se206.g11.models to javafx.fxml;
    exports com.se206.g11;
    exports com.se206.g11.models;
    exports com.se206.g11.controllers;
    exports com.se206.g11.util;
}