package com.se206.g11.components;

import com.se206.g11.MainApp;
import com.se206.g11.models.Word;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class InputField extends TextField{
    private static Label inputField;

    public static void configureInputField(Word word){
        StackPane root = MainApp.getStackPane();

        if (inputField != null){
            root.getChildren().remove(inputField);
        }
        
        inputField = new Label(word.getEnglish());
        inputField.setLayoutX(200);
        inputField.setLayoutY(200);
        root.getChildren().addAll(inputField);
    }
}
