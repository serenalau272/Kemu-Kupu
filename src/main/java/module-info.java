module com {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires transitive javafx.graphics;
    requires java.net.http;
    requires com.google.gson;
    opens com to javafx.fxml;
    opens com.controllers to javafx.fxml;
    opens com.models to com.google.gson;
    opens com.components to javafx.fxml;
    opens com.controllers.modals to javafx.fxml;
    opens com.controllers.views to javafx.fxml;
    opens com.controllers.fxmlComponents to javafx.fxml;
    opens com.util to javafx.fxml;
    opens com.enums to javafx.fxml;
    exports com;
    exports com.models;
    exports com.controllers;
    exports com.controllers.modals;
    exports com.controllers.views;
    exports com.controllers.fxmlComponents;
    exports com.util;
    exports com.components;
    exports com.enums;
    exports com.components.animations;
    exports com.models.APIModels;
}