package com.se206.g11;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;

import java.io.IOException;
/*
Put header here


 */

import com.se206.g11.models.SpellingTopic;
import com.se206.g11.models.Word;

public class ApplicationController {
    private Stage stage;
	private Scene scene;
	private Parent root;

    protected SpellingTopic chosenTopic;
    protected int score;
    protected Word[] wordList;
    //etc.

    protected void transitScene(String fxml, MouseEvent event){
        setRoot(fxml);
        setStage(event);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void setRoot(String fxml){
        try {
            root = FXMLLoader.load((this.getClass().getResource("/fxml/"+fxml + ".fxml")));            
        } catch (IOException exception){
            System.out.println("Unable to load fxml file: " + fxml);
        }
    }

    private void setStage(MouseEvent event) {
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
    }
}


